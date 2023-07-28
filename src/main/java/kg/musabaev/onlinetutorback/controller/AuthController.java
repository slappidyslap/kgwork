package kg.musabaev.onlinetutorback.controller;

import jakarta.validation.Valid;
import kg.musabaev.onlinetutorback.dto.request.AuthenticateRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateTokenRequest;
import kg.musabaev.onlinetutorback.dto.response.AuthenticateOrRefreshResponse;
import kg.musabaev.onlinetutorback.service.AuthenticationService;
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
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

	AuthenticationService authService;

	@PostMapping("/authenticate")
	ResponseEntity<AuthenticateOrRefreshResponse> authenticate(@Valid @RequestBody AuthenticateRequest dto) {
		return authService.authenticate(dto);
	}

	@PostMapping("/refresh")
	ResponseEntity<AuthenticateOrRefreshResponse> refresh(@Valid @RequestBody UpdateTokenRequest dto) {
		return authService.refresh(dto);
	}
}
