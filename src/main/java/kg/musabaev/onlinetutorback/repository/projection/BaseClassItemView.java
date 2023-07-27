package kg.musabaev.onlinetutorback.repository.projection;

import java.time.LocalDate;

public interface BaseClassItemView {
	Long getId();
	String getTitle();
	String getDescription();
	Integer getPrice();
	LocalDate getCreatedDate();
	String getCoverUrl();
	String getClassType();
}