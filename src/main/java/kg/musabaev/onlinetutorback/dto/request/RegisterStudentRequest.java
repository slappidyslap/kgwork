package kg.musabaev.onlinetutorback.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterStudentRequest extends RegisterUserBaseRequest {

	@Positive
	Integer age;
}
