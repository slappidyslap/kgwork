package kg.musabaev.onlinetutorback.dto.response;

import java.time.LocalDate;

public record NewClassResponse(
		Long id,
		LocalDate createdDate
) {
}
