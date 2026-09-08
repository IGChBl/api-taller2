package ni.edu.uam.api_taller2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Clase principal del proyecto.
// @SpringBootApplication activa la configuracion automatica de Spring Boot,
// el escaneo de componentes (@RestController, @Service, @RestControllerAdvice)
// y levanta el servidor web embebido (Tomcat) en el puerto 8080.
@SpringBootApplication
public class ApiTaller2Application {

	// Metodo de entrada: inicia el contexto de Spring y publica la API REST.
	public static void main(String[] args) {
		SpringApplication.run(ApiTaller2Application.class, args);
	}

}
