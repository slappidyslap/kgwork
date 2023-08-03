package kg.musabaev.onlinetutorback.repository.projection;

import kg.musabaev.onlinetutorback.model.User;

import java.time.LocalDate;

public interface UserItemView {
	Long getId();
	String getName();
	String getSurname();
	User.Gender getGender();
	String getPhoneNumber();
	String getEmail();
	String getPassword();
	User.Role getRole();
	String getAvatarUrl();
	String getRegion();
	String getEducation();
	LocalDate getCreatedDate();
}