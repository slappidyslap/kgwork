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

	public static String defaultCoverUrl = "https://memepedia.ru/wp-content/uploads/2018/01/%D1%80%D0%B6%D1%83%D0%BC%D0%B5%D0%BD-%D1%81%D0%BC%D0%B0%D0%B9%D0%BB-%D0%B2-%D0%BE%D1%87%D0%BA%D0%B0%D1%85.jpg";

	public static void main(String[] args) {
		SpringApplication.run(OnlineTutorBackApplication.class, args);
	}

	@Bean
	CommandLineRunner runner1(CategoryRepo categoryRepo) {
		return args -> {
			categoryRepo.deleteAll();
			categoryRepo.saveAll(Stream.of(
					"Изучить английский язык с нуля до TOEFL",
					"Подготовиться к ОРТ, НЦТ и гос. экзаменам",
					"Выполнять домашние задания на продленке",
					"Подготовиться к школе",
					"Подтянуть успеваемость по всем школьным предметам",
					"Подтянуть знание по математики с нуля"
			).map(Category::new).toList());
		};
	}
}
