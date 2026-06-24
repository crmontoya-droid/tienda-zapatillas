package duoc.ventas.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String marca;
    private Double precio;
    private Integer stock;
}