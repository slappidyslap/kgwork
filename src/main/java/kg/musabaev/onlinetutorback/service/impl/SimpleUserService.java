package kg.musabaev.onlinetutorback.service.impl;

import kg.musabaev.onlinetutorback.dto.request.*;
import kg.musabaev.onlinetutorback.dto.response.RegisterUserResponse;
import kg.musabaev.onlinetutorback.exception.ClassNotFoundException;
import kg.musabaev.onlinetutorback.exception.UserNotFoundException;
import kg.musabaev.onlinetutorback.mapper.SpecialistMapper;
import kg.musabaev.onlinetutorback.mapper.StudentMapper;
import kg.musabaev.onlinetutorback.model.Specialist;
import kg.musabaev.onlinetutorback.model.Student;
import kg.musabaev.onlinetutorback.model.User;
import kg.musabaev.onlinetutorback.repository.*;
import kg.musabaev.onlinetutorback.repository.projection.BaseClassItemView;
import kg.musabaev.onlinetutorback.repository.projection.SpecialistItemView;
import kg.musabaev.onlinetutorback.repository.projection.StudentItemView;
import kg.musabaev.onlinetutorback.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;
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
	CommentRepo commentRepo;
	BaseClassRepo baseClassRepo;
	RefreshTokenRepo refreshTokenRepo;
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

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<SpecialistItemView> getSpecialistById(long id) {
		return ResponseEntity.ok(specialistRepo.findProjectedById(id).orElseThrow(UserNotFoundException::new));
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<StudentItemView> getStudentById(long id) {
		return ResponseEntity.ok(studentRepo.findProjectedById(id).orElseThrow(UserNotFoundException::new));
	}

	@Override
	@Transactional
	public ResponseEntity<Void> deleteSpecialistById(long id) {
		throwUserNotFoundIf(() -> !userRepo.existsById(id));

		refreshTokenRepo.deleteByOwnerId(id);
		commentRepo.deleteAllCommentsOfClassesCreatedByAuthorId(id);
		baseClassRepo.deleteAllByAuthorId(id);
		userRepo.deleteById(id);

		return ResponseEntity.noContent().build();
	}

	@Override
	@Transactional
	public ResponseEntity<Void> deleteStudentById(long id) {
		throwUserNotFoundIf(() -> !userRepo.existsById(id));

		refreshTokenRepo.deleteByOwnerId(id);
		commentRepo.deleteAllByAuthorId(id);
		studentRepo.deleteById(id);

		return ResponseEntity.noContent().build();
	}

	@Override
	@Transactional
	public ResponseEntity<Void> updateSpecialistById(long id, UpdateSpecialistRequest dto) {
		Optional<Specialist> persistedSpecialist = specialistRepo.findById(id);
		persistedSpecialist.ifPresent(s -> {
			specialistMapper.update(dto, s);
			specialistRepo.save(s);
		});
		persistedSpecialist.orElseThrow(UserNotFoundException::new);
		return ResponseEntity.noContent().build();
	}

	@Override
	@Transactional
	public ResponseEntity<Void> updateStudentById(long id, UpdateStudentRequest dto) {
		Optional<Student> persistedStudent = studentRepo.findById(id);
		persistedStudent.ifPresent(s -> {
			studentMapper.update(dto, s);
			studentRepo.save(s);
		});
		persistedStudent.orElseThrow(UserNotFoundException::new);
		return ResponseEntity.noContent().build();

	}

	@Override
	@Transactional
	public ResponseEntity<Void> addToFinishedClassesOfStudent(long id, AddOrDeleteClassInStudentList dto) {
		throwClassNotFoundIf(() -> !baseClassRepo.existsById(dto.getClassId()));

		Optional<Student> persistedStudent = studentRepo.findById(id);
		persistedStudent.ifPresent(s -> {
			s.getFinishedClasses().add(baseClassRepo.getReferenceById(dto.getClassId()));
			studentRepo.save(s);
		});
		persistedStudent.orElseThrow(UserNotFoundException::new);
		return ResponseEntity.noContent().build();
	}

	@Override
	@Transactional
	public ResponseEntity<Void> addToInProcessClassesOfStudent(long id, AddOrDeleteClassInStudentList dto) {
		throwClassNotFoundIf(() -> !baseClassRepo.existsById(dto.getClassId()));

		Optional<Student> persistedStudent = studentRepo.findById(id);
		persistedStudent.ifPresent(s -> {
			s.getInProcessClasses().add(baseClassRepo.getReferenceById(dto.getClassId()));
			studentRepo.save(s);
		});
		persistedStudent.orElseThrow(UserNotFoundException::new);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> deleteFromFinishedClassesOfStudent(long id, AddOrDeleteClassInStudentList dto) {
		throwClassNotFoundIf(() -> !baseClassRepo.existsById(dto.getClassId()));

		Optional<Student> persistedStudent = studentRepo.findById(id);
		persistedStudent.ifPresent(s -> {
			s.getFinishedClasses().removeIf(c -> Objects.equals(c.getId(), dto.getClassId()));
			studentRepo.save(s);
		});
		persistedStudent.orElseThrow(UserNotFoundException::new);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> deleteFromInProcessClassesOfStudent(long id, AddOrDeleteClassInStudentList dto) {
		throwClassNotFoundIf(() -> !baseClassRepo.existsById(dto.getClassId()));

		Optional<Student> persistedStudent = studentRepo.findById(id);
		persistedStudent.ifPresent(s -> {
			s.getInProcessClasses().removeIf(c -> Objects.equals(c.getId(), dto.getClassId()));
			studentRepo.save(s);
		});
		persistedStudent.orElseThrow(UserNotFoundException::new);
		return ResponseEntity.noContent().build();
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<Page<BaseClassItemView>> getFinishedClassesOfStudent(long id, Pageable pageable) {
		return ResponseEntity.ok(studentRepo.findAllFinishedClasses(id, pageable));
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<Page<BaseClassItemView>> getInProcessClassesOfStudent(long id, Pageable pageable) {
		return ResponseEntity.ok(studentRepo.findAllInProcessClassesClasses(id, pageable));
	}

	public void throwConflictIf(Supplier<Boolean> function) {
		if (function.get()) throw new ResponseStatusException(CONFLICT);
	}

	public void throwUserNotFoundIf(Supplier<Boolean> function) {
		if (function.get()) throw new UserNotFoundException();
	}

	public void throwClassNotFoundIf(Supplier<Boolean> function) {
		if (function.get()) throw new ClassNotFoundException();
	}
}
