package duoc.reclamos.controller;

import duoc.reclamos.dto.ApiResponse;
import duoc.reclamos.dto.ReclamoDTO;
import duoc.reclamos.model.Reclamo;
import duoc.reclamos.service.ReclamoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reclamos")
public class ReclamoController {

    @Autowired
    private ReclamoService reclamoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Reclamo>>> getAll(HttpServletRequest request) {
        List<Reclamo> reclamos = reclamoService.listarTodos();

        ApiResponse<List<Reclamo>> response = ApiResponse.<List<Reclamo>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Lista de reclamos obtenida exitosamente")
                .path(request.getRequestURI())
                .data(reclamos)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Reclamo>> getById(@PathVariable Long id, HttpServletRequest request) {
        return reclamoService.buscarPorId(id)
                .map(reclamo -> {
                    ApiResponse<Reclamo> response = ApiResponse.<Reclamo>builder()
                            .timestamp(LocalDateTime.now())
                            .status(HttpStatus.OK.value())
                            .message("Reclamo encontrado")
                            .path(request.getRequestURI())
                            .data(reclamo)
                            .build();
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponse<Reclamo> response = ApiResponse.<Reclamo>builder()
                            .timestamp(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Reclamo no encontrado con el ID especificado")
                            .path(request.getRequestURI())
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<ApiResponse<List<Reclamo>>> getByClienteId(@PathVariable Long clienteId, HttpServletRequest request) {
        List<Reclamo> reclamosCliente = reclamoService.buscarPorCliente(clienteId);

        ApiResponse<List<Reclamo>> response = ApiResponse.<List<Reclamo>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Reclamos del cliente obtenidos exitosamente")
                .path(request.getRequestURI())
                .data(reclamosCliente)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Reclamo>> create(@Valid @RequestBody ReclamoDTO dto, HttpServletRequest request) {
        Reclamo reclamo = new Reclamo();
        reclamo.setClienteId(dto.getClienteId());
        reclamo.setVentaId(dto.getVentaId());
        reclamo.setMotivo(dto.getMotivo());
        reclamo.setDescripcion(dto.getDescripcion());

        Reclamo nuevoReclamo = reclamoService.crearReclamo(reclamo);

        ApiResponse<Reclamo> response = ApiResponse.<Reclamo>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .message("Reclamo registrado correctamente")
                .path(request.getRequestURI())
                .data(nuevoReclamo)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<Reclamo>> updateEstado(@PathVariable Long id, @RequestParam String estado, HttpServletRequest request) {
        return reclamoService.cambiarEstado(id, estado)
                .map(reclamo -> {
                    ApiResponse<Reclamo> response = ApiResponse.<Reclamo>builder()
                            .timestamp(LocalDateTime.now())
                            .status(HttpStatus.OK.value())
                            .message("Estado del reclamo actualizado a: " + estado)
                            .path(request.getRequestURI())
                            .data(reclamo)
                            .build();
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponse<Reclamo> response = ApiResponse.<Reclamo>builder()
                            .timestamp(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("No se pudo actualizar. Reclamo no encontrado.")
                            .path(request.getRequestURI())
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }
}