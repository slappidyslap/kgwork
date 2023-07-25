package kg.musabaev.onlinetutorback.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateGroupClassRequest extends UpdateClassBaseRequest {
	@NotNull
	@Future
	Instant startDateTime;
	@NotNull
	@Future
	Instant finishDateTime;
}