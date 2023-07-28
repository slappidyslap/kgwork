package kg.musabaev.onlinetutorback.service;

import kg.musabaev.onlinetutorback.dto.request.RegisterSpecialistRequest;
import kg.musabaev.onlinetutorback.dto.response.RegisterUserResponse;
import org.springframework.http.ResponseEntity;

public interface SpecialistService {
	ResponseEntity<RegisterUserResponse> registerSpecialist(RegisterSpecialistRequest dto);
}
