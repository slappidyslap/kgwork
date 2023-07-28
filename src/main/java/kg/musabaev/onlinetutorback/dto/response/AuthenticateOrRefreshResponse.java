package kg.musabaev.onlinetutorback.dto.response;

import kg.musabaev.onlinetutorback.model.User;

public record AuthenticateOrRefreshResponse(
		String accessToken,
		String refreshToken,
		UserInfo user
) {

	public record UserInfo(
			Long id,
			String name,
			String surname,
			String email,
			String phoneNumber,
			String region,
			String education,
			User.Gender gender,
			User.Role role,
			String avatarUrl) {
	}
}
