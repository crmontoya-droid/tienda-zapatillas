package duoc.ventas.controller;

import duoc.ventas.dto.ApiResponse;
import duoc.ventas.dto.VentaDTO;
import duoc.ventas.service.VentaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@Slf4j
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<VentaDTO>>> listar(HttpServletRequest request) {
        log.info("Peticion REST: Listar historial de ventas");
        List<VentaDTO> lista = ventaService.listarTodas();
        return ResponseEntity.ok(buildResponse(HttpStatus.OK, "Historial obtenido con éxito", request, lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VentaDTO>> obtenerPorId(@PathVariable Long id, HttpServletRequest request) {
        log.info("Peticion REST: Obtener venta ID {}", id);
        VentaDTO venta = ventaService.buscarPorId(id);
        return ResponseEntity.ok(buildResponse(HttpStatus.OK, "Venta encontrada", request, venta));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VentaDTO>> crear(@Valid @RequestBody VentaDTO dto, HttpServletRequest request) {
        log.info("Peticion REST: Realizar nueva venta");
        VentaDTO nueva = ventaService.realizarVenta(dto);
        return new ResponseEntity<>(buildResponse(HttpStatus.CREATED, "Venta procesada exitosamente", request, nueva), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Peticion REST: Eliminar registro de venta ID {}", id);
        ventaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private <T> ApiResponse<T> buildResponse(HttpStatus status, String message, HttpServletRequest request, T data) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .message(message)
                .path(request.getRequestURI())
                .data(data)
                .build();
    }
}