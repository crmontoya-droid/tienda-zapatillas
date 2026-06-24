package duoc.movimientos.client;

import duoc.movimientos.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-productos", url = "http://localhost:8082/api/productos")
public interface ProductoClient {
    @GetMapping("/{id}")
    ApiResponse<Object> obtenerProductoPorId(@PathVariable("id") Long id);
}