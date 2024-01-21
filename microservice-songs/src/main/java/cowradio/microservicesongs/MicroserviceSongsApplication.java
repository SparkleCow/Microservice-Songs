package cowradio.microservicesongs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceSongsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceSongsApplication.class, args);
	}

}
