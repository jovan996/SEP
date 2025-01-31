package rs.grgur.jovan.optika;

import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) {
                SpringApplication app = new SpringApplication(BankApplication.class);
                app.setDefaultProperties(Collections
                    .singletonMap("server.port", "8092"));
		app.run(args);
	}

}
