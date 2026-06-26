package duoc.ventas.config;

import duoc.ventas.model.Venta;
import duoc.ventas.repository.VentaRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(VentaRepository repository) {
        return args -> {
            // Tu script original tenía 3 ventas.
            if (repository.count() <= 3) {
                Faker faker = new Faker(new Locale("es"));
                System.out.println("Iniciando generación de datos falsos para ventas...");

                for (int i = 0; i < 50; i++) {
                    Venta venta = new Venta();

                    // Asignamos un cliente (del 1 al 56) y un producto (del 1 al 55) al azar
                    venta.setClienteId(faker.number().numberBetween(1L, 56L));
                    venta.setProductoId(faker.number().numberBetween(1L, 55L));

                    // El cliente compra entre 1 y 3 pares de zapatillas
                    venta.setCantidad(faker.number().numberBetween(1, 4));

                    // Calculamos un total aproximado realista
                    venta.setTotal((double) faker.number().numberBetween(40000, 300000));

                    repository.save(venta);
                }
                System.out.println("50 Ventas generadas con exito");
            }
        };
    }
}