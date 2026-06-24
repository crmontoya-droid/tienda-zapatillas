package duoc.envios.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EnvioDTO {
    private Long id;

    @NotNull(message = "El ID de la venta es obligatorio")
    private Long ventaId;

    @NotBlank(message = "La dirección de envío es obligatoria")
    private String direccion;

    private String estado; // Se asignará automáticamente como 'PREPARACION'

    private String numeroSeguimiento; // Se generará en el Service

    private LocalDateTime fechaEnvio;
}