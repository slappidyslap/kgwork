package kg.musabaev.onlinetutorback.controller;

import kg.musabaev.onlinetutorback.model.Category;
import kg.musabaev.onlinetutorback.repository.CategoryRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

	CategoryRepo categoryRepo;

	@GetMapping
	ResponseEntity<List<Category>> getCategories() {
		return ResponseEntity.ok(categoryRepo.findAll());
	}
}
