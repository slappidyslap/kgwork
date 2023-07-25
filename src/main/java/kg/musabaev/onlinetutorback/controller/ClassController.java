package kg.musabaev.onlinetutorback.controller;

import kg.musabaev.onlinetutorback.dto.request.NewGroupClassRequest;
import kg.musabaev.onlinetutorback.dto.request.NewIndividualClassRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateGroupClassRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateIndividualClassRequest;
import kg.musabaev.onlinetutorback.dto.response.NewClassResponse;
import kg.musabaev.onlinetutorback.repository.projection.GroupClassListView;
import kg.musabaev.onlinetutorback.repository.projection.IndividualClassListView;
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
	ResponseEntity<NewClassResponse> createGroupClass(@RequestBody NewGroupClassRequest dto) {
		return classService.createGroupClass(dto);
	}

	@PostMapping("/individuals")
	ResponseEntity<NewClassResponse> createIndividualClass(@RequestBody NewIndividualClassRequest dto) {
		return classService.createIndividualClass(dto);
	}

	@PutMapping("/groups/{id}")
	ResponseEntity<Void> updateGroupClass(@PathVariable Long id, @RequestBody UpdateGroupClassRequest dto) {
		return classService.updateGroupClass(id, dto);
	}

	@PutMapping("/individuals/{id}")
	ResponseEntity<Void> updateIndividualClass(@PathVariable Long id, @RequestBody UpdateIndividualClassRequest dto) {
		return classService.updateIndividualClass(id, dto);
	}

	@GetMapping("/groups")
	ResponseEntity<Page<GroupClassListView>> getAllGroupClasses(@ParameterObject @PageableDefault Pageable pageable) {
		return classService.getAllGroupClasses(pageable);
	}

	@GetMapping("/individuals")
	ResponseEntity<Page<IndividualClassListView>> getAllIndividualClasses(@ParameterObject @PageableDefault Pageable pageable) {
		return classService.getAllIndividualClasses(pageable);
	}
}
