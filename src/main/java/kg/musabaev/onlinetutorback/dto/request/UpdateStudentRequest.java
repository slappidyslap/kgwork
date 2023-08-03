package kg.musabaev.onlinetutorback.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateStudentRequest extends UpdateUserBaseRequest {
	@NotNull
	@PositiveOrZero
	Integer age;
}
