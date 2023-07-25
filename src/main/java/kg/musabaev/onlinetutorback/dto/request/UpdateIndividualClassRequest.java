package kg.musabaev.onlinetutorback.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateIndividualClassRequest extends UpdateClassBaseRequest {
	@Positive
	Long durationInSeconds;
}
