package kg.musabaev.onlinetutorback.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NewCommentRequest(
		@NotNull
		@Positive
		Long classId,
		@NotNull
		@NotBlank
		String body
) {
}
