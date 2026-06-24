package duoc.movimientos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoDTO {
    private Long id;

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima debe ser 1")
    private Integer cantidad;

    @NotBlank(message = "El tipo de movimiento (ENTRADA/SALIDA) es obligatorio")
    private String tipo;

    @NotBlank(message = "El motivo del movimiento es obligatorio")
    private String motivo;

    private String descripcion;

    private LocalDateTime fechaMovimiento;
}