package kg.musabaev.onlinetutorback.service;

import kg.musabaev.onlinetutorback.dto.request.PasswordResetRequest;
import kg.musabaev.onlinetutorback.dto.request.PasswordResetTokenRequest;
import org.springframework.http.ResponseEntity;

public interface PasswordResetService {

	ResponseEntity<Void> resetPassword(long userId, PasswordResetRequest dto);

	ResponseEntity<Void> generatePasswordResetToken(PasswordResetTokenRequest dto);
}
