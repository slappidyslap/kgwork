package kg.musabaev.onlinetutorback.controller;

import jakarta.validation.Valid;
import kg.musabaev.onlinetutorback.dto.request.*;
import kg.musabaev.onlinetutorback.dto.response.RegisterUserResponse;
import kg.musabaev.onlinetutorback.repository.projection.BaseClassItemView;
import kg.musabaev.onlinetutorback.repository.projection.SpecialistItemView;
import kg.musabaev.onlinetutorback.repository.projection.StudentItemView;
import kg.musabaev.onlinetutorback.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

	UserService userService;

	@PostMapping("/specialists")
	ResponseEntity<RegisterUserResponse> registerSpecialist(@Valid @RequestBody RegisterSpecialistRequest dto) {
		return userService.registerSpecialist(dto);
	}

	@PostMapping("/students")
	ResponseEntity<RegisterUserResponse> registerStudent(@Valid @RequestBody RegisterStudentRequest dto) {
		return userService.registerStudent(dto);
	}

	@GetMapping("/specialists/{id}")
	ResponseEntity<SpecialistItemView> getSpecialistById(@PathVariable long id) {
		return userService.getSpecialistById(id);
	}

	@GetMapping("/students/{id}")
	ResponseEntity<StudentItemView> getStudentById(@PathVariable long id) {
		return userService.getStudentById(id);
	}

	@DeleteMapping("/specialists/{id}")
	ResponseEntity<Void> deleteSpecialist(@PathVariable long id) {
		return userService.deleteSpecialistById(id);
	}

	@DeleteMapping("/students/{id}")
	ResponseEntity<Void> deleteStudent(@PathVariable long id) {
		return userService.deleteStudentById(id);
	}

	@PatchMapping("/specialists/{id}")
	ResponseEntity<Void> updateSpecialist(
			@PathVariable long id,
			@Valid @RequestBody UpdateSpecialistRequest dto) {
		return userService.updateSpecialistById(id, dto);
	}

	@PatchMapping("/students/{id}")
	ResponseEntity<Void> updateStudent(
			@PathVariable long id,
			@Valid @RequestBody UpdateStudentRequest dto) {
		return userService.updateStudentById(id, dto);
	}

	@PostMapping("/students/{id}/finished-classes")
	ResponseEntity<Void> addToFinishedClassesOfStudent(
			@PathVariable long id,
			@Valid @RequestBody AddOrDeleteClassInStudentList dto) {
		return userService.addToFinishedClassesOfStudent(id, dto);
	}

	@PostMapping("/students/{id}/in-process-classes")
	ResponseEntity<Void> addToInProcessClassesOfStudent(
			@PathVariable long id,
			@Valid @RequestBody AddOrDeleteClassInStudentList dto) {
		return userService.addToInProcessClassesOfStudent(id, dto);
	}

	@DeleteMapping("/students/{id}/finished-classes")
	ResponseEntity<Void> deleteFromFinishedClassesOfStudent(
			@PathVariable long id,
			@Valid @RequestBody AddOrDeleteClassInStudentList dto) {
		return userService.deleteFromFinishedClassesOfStudent(id, dto);
	}

	@DeleteMapping("/students/{id}/in-process-classes")
	ResponseEntity<Void> deleteFromInProcessClassesOfStudent(
			@PathVariable long id,
			@Valid @RequestBody AddOrDeleteClassInStudentList dto) {
		return userService.deleteFromInProcessClassesOfStudent(id, dto);
	}

	@GetMapping("/students/{id}/finished-classes")
	ResponseEntity<Page<BaseClassItemView>> getFinishedClassesOfStudent(
			@PathVariable long id,
			@ParameterObject @PageableDefault Pageable pageable) {
		return userService.getFinishedClassesOfStudent(id, pageable);
	}

	@GetMapping("/students/{id}/in-process-classes")
	ResponseEntity<Page<BaseClassItemView>> getInProcessClassesOfStudent(
			@PathVariable long id,
			@ParameterObject @PageableDefault Pageable pageable) {
		return userService.getInProcessClassesOfStudent(id, pageable);
	}

	@PostMapping("/students/{studentId}/specialists/{specialistId}")
	ResponseEntity<Void> rateSpecialist(
			@PathVariable long studentId,
			@PathVariable long specialistId,
			@Valid @RequestBody RateSpecialist dto
	) {
		return userService.rateSpecialist(studentId, specialistId, dto);
	}
}
