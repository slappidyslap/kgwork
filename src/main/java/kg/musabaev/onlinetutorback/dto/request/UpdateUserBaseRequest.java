package kg.musabaev.onlinetutorback.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.musabaev.onlinetutorback.Application;
import kg.musabaev.onlinetutorback.util.validator.UrlOrNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserBaseRequest {
	@NotNull
	String name;
	@NotNull
	@NotBlank
	String surname;
	@NotNull
	@Email
	String email;
	@NotNull
	@NotBlank
	String phoneNumber;
	@UrlOrNull
	String avatarUrl = Application.defaultAvatarUrl;
	@NotNull
	@NotBlank
	String region;
	@NotNull
	@NotBlank
	String education;
}
