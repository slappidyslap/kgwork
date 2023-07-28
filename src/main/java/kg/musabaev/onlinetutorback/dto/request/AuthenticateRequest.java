package kg.musabaev.onlinetutorback.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticateRequest {
	@NotNull
	@NotBlank
	String login;
	@NotNull
	@NotBlank
	String password;
}
