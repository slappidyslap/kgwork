package kg.musabaev.onlinetutorback.repository.projection;

import java.time.LocalDate;

public interface BaseClassListView {
	Long getId();
	String getTitle();
	String getDescription();
	Integer getPrice();
	LocalDate getCreatedDate();
	String getCoverUrl();
}