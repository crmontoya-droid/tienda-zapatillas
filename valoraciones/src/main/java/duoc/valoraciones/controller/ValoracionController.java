package duoc.valoraciones.controller;

import duoc.valoraciones.dto.ApiResponse;
import duoc.valoraciones.dto.ValoracionDTO;
import duoc.valoraciones.model.Valoracion;
import duoc.valoraciones.service.ValoracionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/valoraciones")
public class ValoracionController {

    @Autowired
    private ValoracionService valoracionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Valoracion>>> getAll(HttpServletRequest request) {
        List<Valoracion> valoraciones = valoracionService.listarTodas();

        ApiResponse<List<Valoracion>> response = ApiResponse.<List<Valoracion>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Lista de valoraciones obtenida exitosamente")
                .path(request.getRequestURI())
                .data(valoraciones)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<ApiResponse<List<Valoracion>>> getByProductoId(@PathVariable Long productoId, HttpServletRequest request) {
        List<Valoracion> valoraciones = valoracionService.buscarPorProducto(productoId);

        ApiResponse<List<Valoracion>> response = ApiResponse.<List<Valoracion>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Valoraciones del producto obtenidas exitosamente")
                .path(request.getRequestURI())
                .data(valoraciones)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Valoracion>> create(@Valid @RequestBody ValoracionDTO dto, HttpServletRequest request) {
        Valoracion valoracion = new Valoracion();
        valoracion.setClienteId(dto.getClienteId());
        valoracion.setProductoId(dto.getProductoId());
        valoracion.setPuntuacion(dto.getPuntuacion());
        valoracion.setComentario(dto.getComentario());

        Valoracion nuevaValoracion = valoracionService.crearValoracion(valoracion);

        ApiResponse<Valoracion> response = ApiResponse.<Valoracion>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .message("Valoración registrada correctamente")
                .path(request.getRequestURI())
                .data(nuevaValoracion)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}