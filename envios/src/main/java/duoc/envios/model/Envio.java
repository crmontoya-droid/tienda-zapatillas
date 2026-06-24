package duoc.envios.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "envios") // Cambiado de ventas a envios
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ventaId;         // Relación lógica con el microservicio de Ventas
    private String direccion;     // Dirección de destino
    private String estado;        // Ejemplo: PREPARACION, EN_CAMINO, ENTREGADO
    private String numeroSeguimiento; // Código generado para el rastreo

    private LocalDateTime fechaEnvio;

    @PrePersist
    protected void onCreate() {
        this.fechaEnvio = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = "PREPARACION"; // Estado inicial por defecto
        }
    }
}