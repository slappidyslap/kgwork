package kg.musabaev.onlinetutorback.service;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;

public interface TokenService {

	String generateAccessToken(String username);

	String generateRefreshToken(Long userId);

	@Nullable
	String getUsernameByAccessToken(String accessToken);

	@Nullable
	String getAccessTokenFromRequest(HttpServletRequest request);


}
