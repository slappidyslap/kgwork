package kg.musabaev.onlinetutorback;

import kg.musabaev.onlinetutorback.model.Category;
import kg.musabaev.onlinetutorback.repository.CategoryRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class OnlineTutorBackApplication {

	public static String defaultCoverUrl = "https://www.seekpng.com/png/full/186-1868460_parental-advisory-explicit-content-logo-png-tricouri-parental.png";

	public static void main(String[] args) {
		SpringApplication.run(OnlineTutorBackApplication.class, args);
	}

	@Bean
	CommandLineRunner runner1(CategoryRepo categoryRepo) {
		return args -> categoryRepo.saveAll(Stream.of(
				"Изучить английский язык с нуля до TOEFL",
				"Подготовиться к ОРТ, НЦТ и гос. экзаменам",
				"Выполнять домашние задания на продленке",
				"Подготовиться к школе",
				"Подтянуть успеваемость по всем школьным предметам",
				"Подтянуть знание по математики с нуля"
		).map(Category::new).toList());
	}
}
