package duoc.productos.controller;

import duoc.productos.dto.ApiResponse;
import duoc.productos.dto.ProductoDTO;
import duoc.productos.service.ProductoService;
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
@RequestMapping("/api/productos")
@Slf4j
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> listar(HttpServletRequest request) {
        log.info("Petición REST: Listar todos los productos");
        List<ProductoDTO> lista = productoService.listarTodos();
        return ResponseEntity.ok(buildResponse(HttpStatus.OK, "Lista de productos obtenida", request, lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoDTO>> obtenerPorId(@PathVariable Long id, HttpServletRequest request) {
        log.info("Petición REST: Obtener producto con ID {}", id);
        ProductoDTO producto = productoService.buscarPorId(id);
        return ResponseEntity.ok(buildResponse(HttpStatus.OK, "Producto encontrado", request, producto));
    }

    @GetMapping("/validar-cliente/{clienteId}")
    public ResponseEntity<ApiResponse<Object>> validarClienteExterno(@PathVariable Long clienteId, HttpServletRequest request) {
        log.info("Petición REST: Validando cliente externo con ID {}", clienteId);
        Object cliente = productoService.verificarClienteExterno(clienteId);
        return ResponseEntity.ok(buildResponse(HttpStatus.OK, "Cliente validado exitosamente", request, cliente));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductoDTO>> crear(@Valid @RequestBody ProductoDTO dto, HttpServletRequest request) {
        log.info("Petición REST: Crear nuevo producto");
        ProductoDTO nuevo = productoService.guardar(dto);
        return new ResponseEntity<>(buildResponse(HttpStatus.CREATED, "Producto creado exitosamente", request, nuevo), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Petición REST: Eliminar producto con ID {}", id);
        productoService.eliminar(id);
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