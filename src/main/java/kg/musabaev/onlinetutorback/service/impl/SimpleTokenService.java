package kg.musabaev.onlinetutorback.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import kg.musabaev.onlinetutorback.model.RefreshToken;
import kg.musabaev.onlinetutorback.repository.RefreshTokenRepo;
import kg.musabaev.onlinetutorback.repository.UserRepo;
import kg.musabaev.onlinetutorback.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Primary
@RequiredArgsConstructor
public class SimpleTokenService implements TokenService {

	private final RefreshTokenRepo refreshTokenRepo;
	private final UserRepo userRepo;

	@Value("${app.security.access-token-expiration-ms}")
	private Long accessTokenExpirationMs;
	@Value("${app.security.refresh-token-expiration-ms}")
	private Long refreshTokenExpirationMs;
	@Value("${app.security.secret-key}")
	private char[] secretKeyAsCharArray;

	private Key secretKey;

	@PostConstruct
	private void init() {
		secretKey = Keys.hmacShaKeyFor(StandardCharsets.UTF_8.encode(CharBuffer.wrap(secretKeyAsCharArray)).array());
	}

	@Override
	public String generateAccessToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(Date.from(Instant.now().plusMillis(accessTokenExpirationMs)))
				.signWith(secretKey, SignatureAlgorithm.HS256)
				.compact();
	}

	@Override
	public String generateRefreshToken(Long userId) {
		if (!userRepo.existsById(userId)) throw new ResponseStatusException(NOT_FOUND);

		return refreshTokenRepo.findByOwnerId(userId).map(refreshToken -> {
			refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpirationMs));
			refreshToken.setToken(UUID.randomUUID().toString());

			return refreshTokenRepo.saveAndFlush(refreshToken).getToken();
		}).orElseGet(() -> refreshTokenRepo
				.save(RefreshToken.builder()
						.token(UUID.randomUUID().toString())
						.expiryDate(Instant.now().plusMillis(refreshTokenExpirationMs))
						.owner(userRepo.getReferenceById(userId))
						.build())
				.getToken());
	}

	@Nullable
	@Override
	public String getUsernameByAccessToken(String accessToken) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(secretKey)
					.build()
					.parseClaimsJws(accessToken)
					.getBody()
					.getSubject();
		} catch (Exception e) {
			return null;
		}
	}

	@Nullable
	@Override
	public String getAccessTokenFromRequest(HttpServletRequest request) {
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		return authorization != null && authorization.startsWith("Bearer") ? authorization.substring(7) : null;
	}
}
