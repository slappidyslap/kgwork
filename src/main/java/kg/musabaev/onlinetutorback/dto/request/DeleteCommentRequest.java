package kg.musabaev.onlinetutorback.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DeleteCommentRequest(@NotNull @Positive Long userId) {
}
