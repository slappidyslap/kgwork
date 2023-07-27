package kg.musabaev.onlinetutorback.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateCommentRequest(
		@NotNull
		@NotBlank
		String body,
		@NotNull
		@Positive
		Long userId) {
}
