package kg.musabaev.onlinetutorback;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
public class OnlineTutorBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineTutorBackApplication.class, args);
	}

	@Value("${spring.datasource.password}")
	private String test;

	@GetMapping
	public String get() {
		return test;
	}

}
