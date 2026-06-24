package duoc.ventas.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas") // Cambiado de productos a ventas
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;   // Relación lógica con Clientes
    private Long productoId;  // Relación lógica con Productos
    private Integer cantidad;
    private Double total;

    private LocalDateTime fechaVenta;

    // Asigna la fecha automáticamente al momento de guardar
    @PrePersist
    protected void onCreate() {
        this.fechaVenta = LocalDateTime.now();
    }
}