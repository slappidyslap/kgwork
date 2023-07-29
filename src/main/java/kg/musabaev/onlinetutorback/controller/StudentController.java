package kg.musabaev.onlinetutorback.controller;

import jakarta.validation.Valid;
import kg.musabaev.onlinetutorback.dto.request.RegisterStudentRequest;
import kg.musabaev.onlinetutorback.dto.response.RegisterUserResponse;
import kg.musabaev.onlinetutorback.service.StudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/students")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentController {

	StudentService studentService;

	@PostMapping
	ResponseEntity<RegisterUserResponse> registerStudent(@Valid @RequestBody RegisterStudentRequest dto) {
		return studentService.registerStudent(dto);
	}

}
