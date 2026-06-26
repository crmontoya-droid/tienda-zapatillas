package duoc.pagos.config;

import duoc.pagos.model.Pago;
import duoc.pagos.repository.PagoRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(PagoRepository repository) {
        return args -> {
            if (repository.count() <= 3) {
                Faker faker = new Faker(new Locale("es"));
                System.out.println("Iniciando generacion de datos falsos para pagos");

                String[] metodos = {"TARJETA_CREDITO", "DEBITO", "TRANSFERENCIA", "MERCADOPAGO"};
                String[] estados = {"APROBADO", "APROBADO", "APROBADO", "PENDIENTE", "RECHAZADO"}; // Mayor probabilidad de aprobado

                for (int i = 0; i < 50; i++) {
                    Pago pago = new Pago();

                    pago.setClienteId(faker.number().numberBetween(1L, 57L));
                    pago.setVentaId(faker.number().numberBetween(1L, 54L));

                    pago.setMonto((double) faker.number().numberBetween(40000, 300000));
                    pago.setMetodoPago(metodos[faker.number().numberBetween(0, metodos.length)]);
                    pago.setEstado(estados[faker.number().numberBetween(0, estados.length)]);

                    repository.save(pago);
                }
                System.out.println("50 Pagos generados con exito");
            }
        };
    }
}