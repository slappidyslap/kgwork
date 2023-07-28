package kg.musabaev.onlinetutorback.controller;

import jakarta.validation.Valid;
import kg.musabaev.onlinetutorback.dto.request.NewGroupClassRequest;
import kg.musabaev.onlinetutorback.dto.request.NewIndividualClassRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateGroupClassRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateIndividualClassRequest;
import kg.musabaev.onlinetutorback.dto.response.NewClassResponse;
import kg.musabaev.onlinetutorback.repository.projection.GroupClassItemView;
import kg.musabaev.onlinetutorback.repository.projection.IndividualClassItemView;
import kg.musabaev.onlinetutorback.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/classes")
public class ClassController {

	private final ClassService classService;

	@PostMapping("/groups")
	ResponseEntity<NewClassResponse> createGroupClass(@Valid @RequestBody NewGroupClassRequest dto) {
		return classService.createGroupClass(dto);
	}

	@PostMapping("/individuals")
	ResponseEntity<NewClassResponse> createIndividualClass(@Valid @RequestBody NewIndividualClassRequest dto) {
		return classService.createIndividualClass(dto);
	}

	@PutMapping("/groups/{id}")
	ResponseEntity<Void> updateGroupClass(@PathVariable Long id, @Valid @RequestBody UpdateGroupClassRequest dto) {
		return classService.updateGroupClass(id, dto);
	}

	@PutMapping("/individuals/{id}")
	ResponseEntity<Void> updateIndividualClass(@PathVariable Long id, @Valid @RequestBody UpdateIndividualClassRequest dto) {
		return classService.updateIndividualClass(id, dto);
	}

	@GetMapping("/groups")
	ResponseEntity<Page<GroupClassItemView>> getAllGroupClasses(
			@ParameterObject @PageableDefault Pageable pageable,
			@RequestParam(name = "categoryId", defaultValue = "0") long categoryId) {
		return classService.getAllGroupClasses(categoryId, pageable);
	}

	@GetMapping("/individuals")
	ResponseEntity<Page<IndividualClassItemView>> getAllIndividualClasses(
			@ParameterObject @PageableDefault Pageable pageable,
			@RequestParam(name = "categoryId", defaultValue = "0") long categoryId) {
		return classService.getAllIndividualClasses(categoryId, pageable);
	}

	@GetMapping("/groups/{id}")
	ResponseEntity<GroupClassItemView> getGroupClassById(@PathVariable long id) {
		return classService.getGroupClassById(id);
	}

	@GetMapping("/individuals/{id}")
	ResponseEntity<IndividualClassItemView> getIndividualClassById(@PathVariable long id) {
		return classService.getIndividualClassById(id);
	}
}
