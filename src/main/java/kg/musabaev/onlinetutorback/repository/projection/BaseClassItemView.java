package kg.musabaev.onlinetutorback.repository.projection;

import kg.musabaev.onlinetutorback.model.Category;

import java.time.LocalDate;

public interface BaseClassItemView {
	Long getId();
	String getTitle();
	String getDescription();
	Integer getPrice();
	LocalDate getCreatedDate();
	Category getCategory();
	String getCoverUrl();
	String getClassType();
}