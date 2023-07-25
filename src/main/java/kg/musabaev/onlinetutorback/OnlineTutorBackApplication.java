package kg.musabaev.onlinetutorback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class OnlineTutorBackApplication {

	public static String defaultCoverUrl = "https://www.seekpng.com/png/full/186-1868460_parental-advisory-explicit-content-logo-png-tricouri-parental.png";

	public static void main(String[] args) {
		SpringApplication.run(OnlineTutorBackApplication.class, args);
	}
}
