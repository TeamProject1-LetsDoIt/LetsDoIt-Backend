package teamproject1.letsdoit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@PropertySource(value = {"classpath:application-security.properties"})
@PropertySource(value = {"classpath:application-swagger.properties"})
public class LetsDoItApplication {

	// Bean 생명주기를 이용한 timezone 설정
	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		SpringApplication.run(LetsDoItApplication.class, args);
	}

}
