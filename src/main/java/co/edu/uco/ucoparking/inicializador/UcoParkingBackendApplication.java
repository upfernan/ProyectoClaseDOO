package co.edu.uco.ucoparking.inicializador;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"co.edu.uco.ucoparking.controlador"})
public class UcoParkingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(UcoParkingBackendApplication.class, args);
	}

}
