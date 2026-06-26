package duoc.productos.config;

import duoc.productos.model.Producto;
import duoc.productos.repository.ProductoRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(ProductoRepository repository) {
        return args -> {
            // Tu script original tenía 5 productos.
            if (repository.count() <= 5) {
                Faker faker = new Faker(new Locale("es"));
                System.out.println("Iniciando generación de datos falsos para productos...");

                // Diccionario de tienda de zapatillas
                String[] marcas = {"Nike", "Adidas", "Puma", "Reebok", "New Balance", "Vans", "Converse", "Under Armour"};
                String[] estilos = {"Air Max", "Zoom", "Classic", "Runner", "Pro", "Elite", "Street", "Retro", "Force"};

                for (int i = 0; i < 50; i++) {
                    Producto producto = new Producto();

                    String marca = marcas[faker.number().numberBetween(0, marcas.length)];
                    String estilo = estilos[faker.number().numberBetween(0, estilos.length)];
                    String nombreZapatilla = marca + " " + estilo + " " + faker.number().numberBetween(1, 99);

                    producto.setNombre(nombreZapatilla);
                    producto.setMarca(marca);

                    // Precio entre $40.000 y $200.000
                    producto.setPrecio((double) faker.number().numberBetween(40000, 200000));
                    // Stock aleatorio entre 5 y 100 unidades
                    producto.setStock(faker.number().numberBetween(5, 100));

                    repository.save(producto);
                }
                System.out.println("50 Zapatillas generadas con exito");
            }
        };
    }
}