package kg.musabaev.onlinetutorback.controller;

import jakarta.validation.Valid;
import kg.musabaev.onlinetutorback.dto.request.RegisterSpecialistRequest;
import kg.musabaev.onlinetutorback.dto.request.RegisterStudentRequest;
import kg.musabaev.onlinetutorback.dto.response.RegisterUserResponse;
import kg.musabaev.onlinetutorback.service.UserService;
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
}
