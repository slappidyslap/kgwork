package kg.musabaev.onlinetutorback.dto.response;

import java.time.LocalDate;

public record NewCommentResponse(
		Long id,
		LocalDate uploadedDate
) {
}
