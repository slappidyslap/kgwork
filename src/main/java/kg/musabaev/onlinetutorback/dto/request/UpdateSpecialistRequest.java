package kg.musabaev.onlinetutorback.dto.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateSpecialistRequest extends UpdateUserBaseRequest {
	@NotNull
	@JsonSetter(nulls = Nulls.SKIP)
	String description = "";
	@NotNull
	@PositiveOrZero
	Integer workExperienceInMonths;
}
