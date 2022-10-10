package teamproject1.letsdoit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LetsDoItApplication {

	public static void main(String[] args) {
		SpringApplication.run(LetsDoItApplication.class, args);
	}

}
