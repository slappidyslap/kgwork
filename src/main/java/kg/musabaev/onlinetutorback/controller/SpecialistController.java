package kg.musabaev.onlinetutorback.controller;

import jakarta.validation.Valid;
import kg.musabaev.onlinetutorback.dto.request.RegisterSpecialistRequest;
import kg.musabaev.onlinetutorback.dto.response.RegisterUserResponse;
import kg.musabaev.onlinetutorback.service.SpecialistService;
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
@RequestMapping("/users/specialists")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpecialistController {

	SpecialistService specialistService;

	@PostMapping
	ResponseEntity<RegisterUserResponse> registerSpecialist(@Valid @RequestBody RegisterSpecialistRequest dto) {
		return specialistService.registerSpecialist(dto);
	}
}
