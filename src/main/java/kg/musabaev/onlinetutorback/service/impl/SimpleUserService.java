package kg.musabaev.onlinetutorback.service.impl;

import kg.musabaev.onlinetutorback.dto.request.RegisterSpecialistRequest;
import kg.musabaev.onlinetutorback.dto.request.RegisterStudentRequest;
import kg.musabaev.onlinetutorback.dto.response.RegisterUserResponse;
import kg.musabaev.onlinetutorback.mapper.SpecialistMapper;
import kg.musabaev.onlinetutorback.mapper.StudentMapper;
import kg.musabaev.onlinetutorback.model.User;
import kg.musabaev.onlinetutorback.repository.SpecialistRepo;
import kg.musabaev.onlinetutorback.repository.StudentRepo;
import kg.musabaev.onlinetutorback.repository.UserRepo;
import kg.musabaev.onlinetutorback.service.UserService;
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
import static org.springframework.http.HttpStatus.CREATED;

@Service
@Primary
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimpleUserService implements UserService {

	UserRepo userRepo;
	StudentRepo studentRepo;
	StudentMapper studentMapper;
	SpecialistRepo specialistRepo;
	SpecialistMapper specialistMapper;
	PasswordEncoder passwordEncoder;

	@Override
	public ResponseEntity<RegisterUserResponse> registerSpecialist(RegisterSpecialistRequest dto) {
		throwConflictIf(() -> userRepo.existsByEmail(dto.getEmail()));
		throwConflictIf(() -> userRepo.existsByPhoneNumber(dto.getPhoneNumber()));

		var newSpecialist = specialistMapper.toModel(dto);
		newSpecialist.setPassword(passwordEncoder.encode(dto.getPassword()));
		newSpecialist.setRole(User.Role.ROLE_SPECIALIST);
		return new ResponseEntity<>(specialistMapper.toDto(specialistRepo.save(newSpecialist)), CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<RegisterUserResponse> registerStudent(RegisterStudentRequest dto) {
		throwConflictIf(() -> userRepo.existsByEmail(dto.getEmail()));
		throwConflictIf(() -> userRepo.existsByPhoneNumber(dto.getPhoneNumber()));

		var newStudent = studentMapper.toModel(dto);
		newStudent.setPassword(passwordEncoder.encode(dto.getPassword()));
		newStudent.setRole(User.Role.ROLE_STUDENT);
		return new ResponseEntity<>(studentMapper.toDto(studentRepo.save(newStudent)), CREATED);
	}

	public void throwConflictIf(Supplier<Boolean> function) {
		if (function.get()) throw new ResponseStatusException(CONFLICT);
	}
}