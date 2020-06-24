package rs.grgur.jovan.patike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import rs.grgur.jovan.patike.config.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class OptikaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OptikaApplication.class, args);
	}

}
