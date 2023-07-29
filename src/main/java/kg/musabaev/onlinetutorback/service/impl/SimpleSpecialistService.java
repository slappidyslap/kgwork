package kg.musabaev.onlinetutorback.service.impl;

import kg.musabaev.onlinetutorback.dto.request.RegisterSpecialistRequest;
import kg.musabaev.onlinetutorback.dto.response.RegisterUserResponse;
import kg.musabaev.onlinetutorback.mapper.SpecialistMapper;
import kg.musabaev.onlinetutorback.model.User;
import kg.musabaev.onlinetutorback.repository.SpecialistRepo;
import kg.musabaev.onlinetutorback.repository.UserRepo;
import kg.musabaev.onlinetutorback.service.SpecialistService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
@Primary
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimpleSpecialistService implements SpecialistService {

	UserRepo userRepo;
	SpecialistRepo specialistRepo;
	SpecialistMapper specialistMapper;
	PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public ResponseEntity<RegisterUserResponse> registerSpecialist(RegisterSpecialistRequest dto) {
		if (userRepo.existsByEmail(dto.getEmail()))
			throw new ResponseStatusException(CONFLICT);

		var newSpecialist = specialistMapper.toModel(dto);
		newSpecialist.setPassword(passwordEncoder.encode(dto.getPassword()));
		newSpecialist.setRole(User.Role.SPECIALIST);
		return ResponseEntity.ok(specialistMapper.toDto(specialistRepo.save(newSpecialist)));
	}
}
