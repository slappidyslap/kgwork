package kg.musabaev.onlinetutorback.controller;

import jakarta.validation.Valid;
import kg.musabaev.onlinetutorback.dto.request.RegisterSpecialistRequest;
import kg.musabaev.onlinetutorback.dto.response.RegisterUserResponse;
import kg.musabaev.onlinetutorback.service.SpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/specialists")
public class SpecialistController {

	private final SpecialistService specialistService;

	@PostMapping
	ResponseEntity<RegisterUserResponse> registerSpecialist(@Valid @RequestBody RegisterSpecialistRequest dto) {
		return specialistService.registerSpecialist(dto);
	}
}
