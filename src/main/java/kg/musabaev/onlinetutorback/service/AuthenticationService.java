package kg.musabaev.onlinetutorback.service;

import kg.musabaev.onlinetutorback.dto.request.AuthenticateRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateTokenRequest;
import kg.musabaev.onlinetutorback.dto.response.AuthenticateOrRefreshResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

	ResponseEntity<AuthenticateOrRefreshResponse> authenticate(AuthenticateRequest request);

	ResponseEntity<AuthenticateOrRefreshResponse> refresh(UpdateTokenRequest request);
}
