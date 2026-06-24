package duoc.valoraciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ValoracionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValoracionesApplication.class, args);
	}

}
