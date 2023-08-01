package kg.musabaev.onlinetutorback.service;

import kg.musabaev.onlinetutorback.dto.request.RegisterSpecialistRequest;
import kg.musabaev.onlinetutorback.dto.request.RegisterStudentRequest;
import kg.musabaev.onlinetutorback.dto.response.RegisterUserResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

	ResponseEntity<RegisterUserResponse> registerSpecialist(RegisterSpecialistRequest dto);

	ResponseEntity<RegisterUserResponse> registerStudent(RegisterStudentRequest dto);
}
