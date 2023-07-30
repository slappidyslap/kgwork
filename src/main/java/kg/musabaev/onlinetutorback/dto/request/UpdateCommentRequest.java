package kg.musabaev.onlinetutorback.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCommentRequest(
		@NotNull
		@NotBlank
		String body) {
}
