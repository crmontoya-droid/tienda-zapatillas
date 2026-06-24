package duoc.ventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients // Requisito para habilitar la comunicación remota
public class VentasApplication {
    public static void main(String[] args) {
        SpringApplication.run(VentasApplication.class, args);
    }
}
