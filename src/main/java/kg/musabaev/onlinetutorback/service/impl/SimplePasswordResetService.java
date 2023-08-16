package kg.musabaev.onlinetutorback.service.impl;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import jakarta.mail.MessagingException;
import kg.musabaev.onlinetutorback.dto.request.PasswordResetRequest;
import kg.musabaev.onlinetutorback.dto.request.PasswordResetTokenRequest;
import kg.musabaev.onlinetutorback.exception.UserNotFoundException;
import kg.musabaev.onlinetutorback.model.PasswordResetToken;
import kg.musabaev.onlinetutorback.model.User;
import kg.musabaev.onlinetutorback.repository.PasswordResetTokenRepo;
import kg.musabaev.onlinetutorback.repository.UserRepo;
import kg.musabaev.onlinetutorback.service.PasswordResetService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Service
@Primary
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class SimplePasswordResetService implements PasswordResetService {

	final JavaMailSender mailSender;
	final BCryptPasswordEncoder encoder;
	final PasswordResetTokenRepo passwordResetTokenRepo;
	final UserRepo userRepo;

	@Value("${app.security.password-reset-token-length}")
	static int TOKEN_LENGTH;
	@Value("${password-reset-token-expiration-ms}")
	static long expiryDatetime;

	@Override
	@Transactional
	public ResponseEntity<Void> resetPassword(long userId, PasswordResetRequest dto) {
		passwordResetTokenRepo.findByToken(dto.getToken())
				.filter(t -> t.getExpiryDate().isBefore(Instant.now()))
				.orElseThrow(() -> new ResponseStatusException(BAD_REQUEST));
		passwordResetTokenRepo.deleteByToken(dto.getToken());

		User owner = userRepo.findById(userId).orElseThrow(UserNotFoundException::new);
		owner.setPassword(encoder.encode(dto.getPassword()));

		return ResponseEntity.noContent().build();
	}

	@Override
	@Transactional
	public ResponseEntity<Void> generatePasswordResetToken(PasswordResetTokenRequest dto) {
		byte[] randomBytes = new byte[TOKEN_LENGTH];
		new SecureRandom().nextBytes(randomBytes);
		String token = encoder.encode(new String(randomBytes, StandardCharsets.UTF_8));

		User owner = userRepo.findByEmail(dto.getUsername())
				.orElseThrow(() -> new ResponseStatusException(NO_CONTENT));

		passwordResetTokenRepo.save(PasswordResetToken.builder()
				.token(token)
				.owner(owner)
				.expiryDate(Instant.now().plusMillis(expiryDatetime))
				.build());

		String mailContent = makeResetPasswordPage(dto.getRedirectUri(), token);
		sendEmail(mailContent, dto.getUsername());

		return ResponseEntity.noContent().build();
	}

	@Async
	private void sendEmail(String content, String email) {
		var mimeMessage = mailSender.createMimeMessage();
		var mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		try {
			mimeMessageHelper.setFrom("noreply@kgwork.com");
			mimeMessageHelper.setSubject("Сброс пароля");
			mimeMessageHelper.setText(content, true);
			mimeMessageHelper.setTo(email);
		} catch (MessagingException e) {
			log.warn("An exception was occurred when sending mail");
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
		}
		mailSender.send(mimeMessage);
	}

	private String makeResetPasswordPage(String redirectUri, String passwordResetToken) {
		Template compile = Mustache.compiler().compile("reset-password.html");

		String formattedExpiryDatetime = "15 минут";
		final String durationAsStr = Duration.ofMillis(expiryDatetime).toString();
		final char c = durationAsStr.charAt(durationAsStr.length() - 1);
		final String timeUnit = durationAsStr.substring(2, durationAsStr.length() - 2);
		if (c == 'H') formattedExpiryDatetime = "%s часов".formatted(timeUnit);
		else if (c == 'M') formattedExpiryDatetime = "%s минут".formatted(timeUnit);
		else if (c == 'S') formattedExpiryDatetime = "%s секунд".formatted(timeUnit);

		var context = Map.of(
				"redirectUri", redirectUri,
				"token", passwordResetToken,
				"expiryDatetime", formattedExpiryDatetime
		);
		return compile.execute(context);
	}
}
