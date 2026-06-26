package duoc.pagos.controller;

import duoc.pagos.dto.ApiResponse;
import duoc.pagos.dto.PagoDTO;
import duoc.pagos.model.Pago;
import duoc.pagos.service.PagoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Pago>>> getAll(HttpServletRequest request) {
        List<Pago> pagos = pagoService.listarTodos();

        ApiResponse<List<Pago>> response = ApiResponse.<List<Pago>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Lista de pagos obtenida exitosamente")
                .path(request.getRequestURI())
                .data(pagos)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Pago>> getById(@PathVariable Long id, HttpServletRequest request) {
        return pagoService.buscarPorId(id)
                .map(pago -> {
                    ApiResponse<Pago> response = ApiResponse.<Pago>builder()
                            .timestamp(LocalDateTime.now())
                            .status(HttpStatus.OK.value())
                            .message("Pago encontrado")
                            .path(request.getRequestURI())
                            .data(pago)
                            .build();
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponse<Pago> response = ApiResponse.<Pago>builder()
                            .timestamp(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Pago no encontrado con el ID especificado")
                            .path(request.getRequestURI())
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Pago>> create(@Valid @RequestBody PagoDTO dto, HttpServletRequest request) {
        Pago pago = new Pago();
        pago.setClienteId(dto.getClienteId());
        pago.setVentaId(dto.getVentaId());
        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());

        Pago nuevoPago = pagoService.procesarPago(pago);

        ApiResponse<Pago> response = ApiResponse.<Pago>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .message("Pago registrado correctamente")
                .path(request.getRequestURI())
                .data(nuevoPago)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<Pago>> updateEstado(@PathVariable Long id, @RequestParam String estado, HttpServletRequest request) {
        return pagoService.actualizarEstado(id, estado)
                .map(pago -> {
                    ApiResponse<Pago> response = ApiResponse.<Pago>builder()
                            .timestamp(LocalDateTime.now())
                            .status(HttpStatus.OK.value())
                            .message("Estado del pago actualizado a: " + estado)
                            .path(request.getRequestURI())
                            .data(pago)
                            .build();
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponse<Pago> response = ApiResponse.<Pago>builder()
                            .timestamp(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("No se pudo actualizar. Pago no encontrado.")
                            .path(request.getRequestURI())
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }
}