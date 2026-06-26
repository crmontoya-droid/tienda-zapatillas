package duoc.movimientos.config;

import duoc.movimientos.model.Movimiento;
import duoc.movimientos.repository.MovimientoRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(MovimientoRepository repository) {
        return args -> {
            // Tu script original tenía 3 movimientos.
            if (repository.count() <= 3) {
                Faker faker = new Faker(new Locale("es"));
                System.out.println("Iniciando generación de datos falsos para movimientos de inventario...");

                for (int i = 0; i < 50; i++) {
                    Movimiento mov = new Movimiento();

                    // Apunta a uno de tus 55 productos
                    mov.setProductoId(faker.number().numberBetween(1L, 55L));

                    // 50% de probabilidad de que sea ENTRADA o SALIDA
                    boolean isEntrada = faker.bool().bool();

                    if (isEntrada) {
                        mov.setTipo("ENTRADA");
                        mov.setMotivo("REPOSICION");
                        mov.setCantidad(faker.number().numberBetween(10, 50));
                        mov.setDescripcion("Ingreso de stock por nuevo lote del proveedor");
                    } else {
                        mov.setTipo("SALIDA");
                        mov.setMotivo("VENTA");
                        mov.setCantidad(faker.number().numberBetween(1, 3));
                        mov.setDescripcion("Despacho de inventario por venta en tienda");
                    }

                    repository.save(mov);
                }
                System.out.println("50 Movimientos generados con exito");
            }
        };
    }
}