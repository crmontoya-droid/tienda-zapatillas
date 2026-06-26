package duoc.valoraciones.config;

import duoc.valoraciones.model.Valoracion;
import duoc.valoraciones.repository.ValoracionRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(ValoracionRepository repository) {
        return args -> {
            if (repository.count() <= 3) {
                Faker faker = new Faker(new Locale("es"));
                System.out.println("Iniciando generación de datos falsos para valoraciones...");

                for (int i = 0; i < 50; i++) {
                    Valoracion valoracion = new Valoracion();

                    // Ahora apunta correctamente a tus 56 clientes y 55 productos reales
                    valoracion.setClienteId(faker.number().numberBetween(1L, 57L));
                    valoracion.setProductoId(faker.number().numberBetween(1L, 56L));

                    // Puntuación de 1 a 5 estrellas
                    valoracion.setPuntuacion(faker.number().numberBetween(1, 6));

                    String[] comentarios = {
                            "Muy cómodas, me encantaron.",
                            "El color no es exactamente como en la foto, pero están bien.",
                            "Llegaron súper rápido, excelente servicio.",
                            "Son un poco ajustadas, recomiendo pedir una talla más.",
                            "La calidad de los materiales es increíble.",
                            "La suela se siente muy resistente para correr.",
                            "No me gustaron tanto en persona, pero cumplen su función.",
                            "Tercer par que compro de esta marca, nunca decepciona."
                    };
                    valoracion.setComentario(comentarios[faker.number().numberBetween(0, comentarios.length)]);

                    repository.save(valoracion);
                }
                System.out.println("50 Valoraciones generadas con exito");
            }
        };
    }
}