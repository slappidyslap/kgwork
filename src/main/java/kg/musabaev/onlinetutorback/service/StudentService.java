package kg.musabaev.onlinetutorback.service;

import kg.musabaev.onlinetutorback.dto.request.RegisterStudentRequest;
import kg.musabaev.onlinetutorback.dto.response.RegisterUserResponse;
import org.springframework.http.ResponseEntity;

public interface StudentService {
	ResponseEntity<RegisterUserResponse> registerStudent(RegisterStudentRequest dto);
}
