package kg.musabaev.onlinetutorback.dto.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.musabaev.onlinetutorback.model.User;
import kg.musabaev.onlinetutorback.util.validator.UrlOrNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterSpecialistRequest {
	@NotNull
	@NotBlank
	String name;
	@NotNull
	@NotBlank
	String surname;
	@NotNull
	User.Gender gender;
	@NotNull
	@NotBlank
	String phoneNumber;
	@NotNull
	@Email
	String email;
	@NotNull
	@NotBlank
	String password;
	@UrlOrNull
	@JsonSetter(nulls = Nulls.SKIP)
	String avatarUrl = "https://www.testhouse.net/wp-content/uploads/2021/11/default-avatar.jpg";
	@JsonSetter(nulls = Nulls.SKIP)
	String region = "";
	@JsonSetter(nulls = Nulls.SKIP)
	String education = "";
	@NotNull
	@JsonSetter(nulls = Nulls.SKIP)
	String description = "";
	@NotNull
	Integer workExperienceInMonths;
}
