package duoc.clientes.controller;

import duoc.clientes.dto.ApiResponse;
import duoc.clientes.dto.ClienteDTO;
import duoc.clientes.service.ClienteService;
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
@RequestMapping("/api/clientes")
@Slf4j
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClienteDTO>>> listar(HttpServletRequest request) {
        log.info("Petición REST: Listar todos los clientes");
        List<ClienteDTO> lista = service.listarTodos();
        return ResponseEntity.ok(buildResponse(HttpStatus.OK, "Lista obtenida", request, lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteDTO>> buscarPorId(@PathVariable Long id, HttpServletRequest request) {
        log.info("Petición REST: Buscando cliente con ID {}", id);
        ClienteDTO cliente = service.buscarPorId(id);
        return ResponseEntity.ok(buildResponse(HttpStatus.OK, "Cliente encontrado", request, cliente));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClienteDTO>> crear(@Valid @RequestBody ClienteDTO dto, HttpServletRequest request) {
        log.info("Petición REST: Creando cliente con email {}", dto.getEmail());
        ClienteDTO nuevo = service.guardar(dto);
        return new ResponseEntity<>(buildResponse(HttpStatus.CREATED, "Cliente registrado exitosamente", request, nuevo), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Petición REST: Solicitud para eliminar ID {}", id);
        service.eliminar(id);
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