package nsv.com.nsvserver;

import nsv.com.nsvserver.Repository.BinDao;
import nsv.com.nsvserver.Repository.TicketDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class NsvServerApplication {
	@Value("${server.port}")
	private String port;

	BinDao binDaoImpl;
	TicketDao ticketDaoImpl;

//	@Autowired
//	ProductRepository productRepository;

	@Autowired
	public NsvServerApplication(BinDao binDaoImpl, TicketDao ticketDaoImpl) {
		this.binDaoImpl = binDaoImpl;
		this.ticketDaoImpl = ticketDaoImpl;
	}

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
