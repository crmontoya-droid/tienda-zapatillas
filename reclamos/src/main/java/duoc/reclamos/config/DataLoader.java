package duoc.reclamos.config;

import duoc.reclamos.model.Reclamo;
import duoc.reclamos.repository.ReclamoRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(ReclamoRepository repository) {
        return args -> {
            if (repository.count() <= 3) {
                Faker faker = new Faker(new Locale("es"));
                System.out.println("Iniciando generacion de datos falsos para reclamos");

                String[] motivos = {"PRODUCTO_DEFECTUOSO", "TALLA_INCORRECTA", "RETRASO_ENVIO", "MALA_ATENCION", "CAJA_ROTA"};
                String[] estados = {"ABIERTO", "EN_REVISION", "CERRADO", "RESUELTO"};

                for (int i = 0; i < 50; i++) {
                    Reclamo reclamo = new Reclamo();

                    reclamo.setClienteId(faker.number().numberBetween(1L, 57L));
                    reclamo.setVentaId(faker.number().numberBetween(1L, 54L));

                    reclamo.setMotivo(motivos[faker.number().numberBetween(0, motivos.length)]);
                    reclamo.setDescripcion(faker.lorem().sentence(15)); // Genera un texto de queja simulado
                    reclamo.setEstado(estados[faker.number().numberBetween(0, estados.length)]);

                    repository.save(reclamo);
                }
                System.out.println("50 Reclamos generados con exito");
            }
        };
    }
}