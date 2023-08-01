package kg.musabaev.onlinetutorback.repository.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public interface CommentItemView {
	Long getId();
	String getBody();
	LocalDate getUploadedDate();
	AuthorInfo getAuthor();

	interface AuthorInfo {
		Long getId();
		@Value("#{target.email}")
		String getUsername();
	}
}
