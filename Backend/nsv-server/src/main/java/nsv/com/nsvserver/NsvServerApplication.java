package nsv.com.nsvserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NsvServerApplication {
	@Value("${server.port}")
	private String port;

	public static void main(String[] args) {

		ApplicationContext theApplicationContext = SpringApplication.run(NsvServerApplication.class, args);

	}
	@Bean
	public CommandLineRunner commandLineRunner(String[] args) {
		return runner->{
			System.out.println("System is running on port: "+port);
		};
	}
}
