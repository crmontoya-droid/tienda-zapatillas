package duoc.envios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients // Requisito para habilitar la comunicación remota
public class EnviosApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnviosApplication.class, args);
    }
}
