package duoc.reclamos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReclamoDTO {
    private Long id;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El ID de la venta es obligatorio")
    private Long ventaId;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    private String estado;
    private LocalDateTime fechaCreacion;
}