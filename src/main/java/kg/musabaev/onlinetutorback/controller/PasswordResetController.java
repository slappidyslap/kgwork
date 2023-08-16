package kg.musabaev.onlinetutorback.controller;

import jakarta.validation.Valid;
import kg.musabaev.onlinetutorback.dto.request.PasswordResetRequest;
import kg.musabaev.onlinetutorback.dto.request.PasswordResetTokenRequest;
import kg.musabaev.onlinetutorback.service.PasswordResetService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordResetController {

	PasswordResetService passwordResetService;

	@PostMapping("/password-token")
	ResponseEntity<Void> generatePasswordResetToken(
			@Valid @RequestBody PasswordResetTokenRequest dto) {
		return passwordResetService.generatePasswordResetToken(dto);
	}

	@PutMapping("/{userId}/password")
	ResponseEntity<Void> resetPassword(
			@PathVariable long userId,
			@Valid @RequestBody PasswordResetRequest dto) {
		return passwordResetService.resetPassword(userId, dto);
	}
}
