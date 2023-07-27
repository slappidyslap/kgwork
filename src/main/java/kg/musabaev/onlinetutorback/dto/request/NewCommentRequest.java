package kg.musabaev.onlinetutorback.dto.request;

public record NewCommentRequest(
		Long userId,
		Long classId,
		String body
) {
}
