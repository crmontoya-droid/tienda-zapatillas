package duoc.clientes.config;

import duoc.clientes.model.Cliente;
import duoc.clientes.repository.ClienteRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Locale;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(ClienteRepository repository) {
        return args -> {
            // Tu script original tenía 6 clientes. Si hay 6 o menos, rellenamos.
            if (repository.count() <= 6) {
                Faker faker = new Faker(new Locale("es"));
                System.out.println("Iniciando generación de datos falsos para clientes...");

                for (int i = 0; i < 50; i++) {
                    Cliente cliente = new Cliente();
                    cliente.setNombre(faker.name().firstName());
                    cliente.setApellido(faker.name().lastName());

                    // Generar un correo único y sin espacios usando nombre y apellido
                    String email = cliente.getNombre().toLowerCase() + "." +
                            cliente.getApellido().toLowerCase() +
                            faker.number().numberBetween(1, 999) + "@cliente.cl";
                    // Limpiar posibles tildes o caracteres raros
                    email = email.replaceAll("[áéíóúñ]", "x");
                    cliente.setEmail(email);

                    // Fecha de nacimiento aleatoria entre 1970 y 2005 (mayores de edad)
                    int year = faker.number().numberBetween(1970, 2005);
                    int month = faker.number().numberBetween(1, 12);
                    int day = faker.number().numberBetween(1, 28); // Hasta el 28 para evitar errores con febrero
                    cliente.setFechaNacimiento(LocalDate.of(year, month, day));

                    repository.save(cliente);
                }
                System.out.println("50 Clientes generados con exito");
            }
        };
    }
}