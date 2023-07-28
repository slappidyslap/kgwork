package kg.musabaev.onlinetutorback.dto.response;

import java.time.LocalDate;

public record RegisterUserResponse(
		Long id,
		LocalDate createdDate
) {

}
