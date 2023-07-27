package kg.musabaev.onlinetutorback.repository.projection;

import java.time.LocalDate;

public interface CommentItemView {
	Long getId();
	Long getUserId();
	String getBody();
	LocalDate getUploadedDate();
}
