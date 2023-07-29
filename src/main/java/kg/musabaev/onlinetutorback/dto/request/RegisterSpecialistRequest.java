package kg.musabaev.onlinetutorback.dto.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterSpecialistRequest extends RegisterUserBaseRequest {
	@NotNull
	@JsonSetter(nulls = Nulls.SKIP)
	String description = "";
	@NotNull
	Integer workExperienceInMonths;
}
