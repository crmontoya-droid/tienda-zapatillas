package duoc.envios.config;

import duoc.envios.model.Envio;
import duoc.envios.repository.EnvioRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(EnvioRepository repository) {
        return args -> {
            if (repository.count() <= 3) {
                Faker faker = new Faker(new Locale("es", "CL"));
                System.out.println("Iniciando generacion de datos falsos para envios");

                String[] estados = {"PREPARACION", "EN_CAMINO", "ENTREGADO", "RETRASADO"};

                for (int i = 0; i < 50; i++) {
                    Envio envio = new Envio();

                    // Apunta a una de tus 53 ventas
                    envio.setVentaId(faker.number().numberBetween(1L, 54L));

                    // Genera una dirección chilena realista
                    envio.setDireccion(faker.address().streetAddress() + ", " + faker.address().city());
                    envio.setEstado(estados[faker.number().numberBetween(0, estados.length)]);

                    // Genera un código de seguimiento profesional
                    envio.setNumeroSeguimiento("TRK-" + faker.number().digits(6) + "-" + faker.name().lastName().toUpperCase());

                    repository.save(envio);
                }
                System.out.println("50 Envios generados con exito");
            }
        };
    }
}