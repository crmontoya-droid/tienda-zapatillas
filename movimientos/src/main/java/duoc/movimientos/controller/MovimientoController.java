package duoc.movimientos.controller;

import duoc.movimientos.dto.ApiResponse;
import duoc.movimientos.dto.MovimientoDTO;
import duoc.movimientos.service.MovimientoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovimientoDTO>>> listar(HttpServletRequest request) {
        List<MovimientoDTO> lista = service.listarTodos();
        return ResponseEntity.ok(buildResponse(HttpStatus.OK, "Historial obtenido", request, lista));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MovimientoDTO>> crear(@Valid @RequestBody MovimientoDTO dto, HttpServletRequest request) {
        MovimientoDTO nueva = service.registrar(dto);
        return new ResponseEntity<>(buildResponse(HttpStatus.CREATED, "Movimiento registrado", request, nueva), HttpStatus.CREATED);
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