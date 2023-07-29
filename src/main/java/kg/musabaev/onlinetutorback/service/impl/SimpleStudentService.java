package kg.musabaev.onlinetutorback.service.impl;

import kg.musabaev.onlinetutorback.dto.request.RegisterStudentRequest;
import kg.musabaev.onlinetutorback.dto.response.RegisterUserResponse;
import kg.musabaev.onlinetutorback.mapper.StudentMapper;
import kg.musabaev.onlinetutorback.model.User;
import kg.musabaev.onlinetutorback.repository.StudentRepo;
import kg.musabaev.onlinetutorback.repository.UserRepo;
import kg.musabaev.onlinetutorback.service.StudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
@Primary
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimpleStudentService implements StudentService {

	UserRepo userRepo;
	StudentRepo studentRepo;
	StudentMapper studentMapper;
	PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public ResponseEntity<RegisterUserResponse> registerStudent(RegisterStudentRequest dto) {
		throwConflictIf(() -> userRepo.existsByEmail(dto.getEmail()));
		throwConflictIf(() -> userRepo.existsByPhoneNumber(dto.getPhoneNumber()));

		var newStudent = studentMapper.toModel(dto);
		newStudent.setPassword(passwordEncoder.encode(dto.getPassword()));
		newStudent.setRole(User.Role.STUDENT);
		return ResponseEntity.ok(studentMapper.toDto(studentRepo.save(newStudent)));
	}

	public void throwConflictIf(Supplier<Boolean> function) {
		if (function.get()) throw new ResponseStatusException(CONFLICT);
	}
}
