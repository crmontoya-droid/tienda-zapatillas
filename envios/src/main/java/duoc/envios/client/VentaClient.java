package duoc.envios.client;

import duoc.envios.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-ventas", url = "http://localhost:8083/api/ventas")
public interface VentaClient {
    @GetMapping("/{id}")
    ApiResponse<Object> obtenerVentaPorId(@PathVariable("id") Long id);
}