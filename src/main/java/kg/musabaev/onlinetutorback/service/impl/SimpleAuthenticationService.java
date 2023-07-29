package kg.musabaev.onlinetutorback.service.impl;

import jakarta.transaction.Transactional;
import kg.musabaev.onlinetutorback.dto.request.AuthenticateRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateTokenRequest;
import kg.musabaev.onlinetutorback.dto.response.AuthenticateOrRefreshResponse;
import kg.musabaev.onlinetutorback.mapper.UserMapper;
import kg.musabaev.onlinetutorback.model.RefreshToken;
import kg.musabaev.onlinetutorback.model.User;
import kg.musabaev.onlinetutorback.repository.RefreshTokenRepo;
import kg.musabaev.onlinetutorback.repository.UserRepo;
import kg.musabaev.onlinetutorback.service.AuthenticationService;
import kg.musabaev.onlinetutorback.service.TokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Primary
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimpleAuthenticationService implements AuthenticationService {

	RefreshTokenRepo refreshTokenRepo;
	UserRepo userRepo;
	TokenService tokenService;
	AuthenticationManager authenticationManager;
	UserMapper userMapper;

	@Override
	@Transactional
	public ResponseEntity<AuthenticateOrRefreshResponse> authenticate(AuthenticateRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
		User foundUser = userRepo
				.findByEmail(request.getLogin())
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
		return ResponseEntity.ok(buildAuthenticationResponse(foundUser));
	}

	@Override
	@Transactional
	public ResponseEntity<AuthenticateOrRefreshResponse> refresh(UpdateTokenRequest request) {
		RefreshToken refreshToken = refreshTokenRepo
				.findByToken(request.getRefreshToken())
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
		refreshTokenRepo.deleteById(refreshToken.getId());

		if (Instant.now().isAfter(refreshToken.getExpiryDate()))
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "refresh token was expired");

		return ResponseEntity.ok(buildAuthenticationResponse(refreshToken.getOwner()));
	}

	private AuthenticateOrRefreshResponse buildAuthenticationResponse(User user) {
		return new AuthenticateOrRefreshResponse(
				tokenService.generateAccessToken(user.getUsername()),
				tokenService.generateRefreshToken(user.getId()),
				userMapper.toDto(user)
		);
	}
}
