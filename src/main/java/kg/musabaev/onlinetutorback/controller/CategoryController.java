package kg.musabaev.onlinetutorback.controller;

import kg.musabaev.onlinetutorback.model.Category;
import kg.musabaev.onlinetutorback.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@CrossOrigin(originPatterns = "*")
public class CategoryController {

	private final CategoryRepo categoryRepo;

	@GetMapping
	ResponseEntity<List<Category>> getCategories() {
		return ResponseEntity.ok(categoryRepo.findAll());
	}
}
