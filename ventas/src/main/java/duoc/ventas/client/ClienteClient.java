package duoc.ventas.client;

import duoc.ventas.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-clientes", url = "http://localhost:8081/api/clientes")
public interface ClienteClient {

    @GetMapping("/{id}")
    ApiResponse<Object> obtenerClientePorId(@PathVariable("id") Long id);
}