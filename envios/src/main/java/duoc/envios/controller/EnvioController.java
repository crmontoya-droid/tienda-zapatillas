package duoc.envios.controller;

import duoc.envios.dto.ApiResponse;
import duoc.envios.dto.EnvioDTO;
import duoc.envios.service.EnvioService;
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
@RequestMapping("/api/envios")
@Slf4j
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EnvioDTO>>> listar(HttpServletRequest request) {
        log.info("Petición REST: Listar todos los envíos");
        List<EnvioDTO> lista = envioService.listarTodos();
        return ResponseEntity.ok(buildResponse(HttpStatus.OK, "Lista de envíos obtenida", request, lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EnvioDTO>> obtenerPorId(@PathVariable Long id, HttpServletRequest request) {
        log.info("Petición REST: Obtener envío ID {}", id);
        EnvioDTO envio = envioService.buscarPorId(id);
        return ResponseEntity.ok(buildResponse(HttpStatus.OK, "Envío encontrado", request, envio));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EnvioDTO>> crear(@Valid @RequestBody EnvioDTO dto, HttpServletRequest request) {
        log.info("Petición REST: Programar nuevo envío");
        EnvioDTO nuevo = envioService.programarEnvio(dto);
        return new ResponseEntity<>(buildResponse(HttpStatus.CREATED, "Envío programado exitosamente", request, nuevo), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Petición REST: Eliminar envío ID {}", id);
        envioService.eliminar(id);
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