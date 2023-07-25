package kg.musabaev.onlinetutorback.service.impl;

import kg.musabaev.onlinetutorback.dto.request.NewGroupClassRequest;
import kg.musabaev.onlinetutorback.dto.request.NewIndividualClassRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateGroupClassRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateIndividualClassRequest;
import kg.musabaev.onlinetutorback.dto.response.NewClassResponse;
import kg.musabaev.onlinetutorback.mapper.ClassMapper;
import kg.musabaev.onlinetutorback.model.GroupClass;
import kg.musabaev.onlinetutorback.model.IndividualClass;
import kg.musabaev.onlinetutorback.repository.GroupClassRepo;
import kg.musabaev.onlinetutorback.repository.IndividualClassRepo;
import kg.musabaev.onlinetutorback.repository.projection.GroupClassItemView;
import kg.musabaev.onlinetutorback.repository.projection.IndividualClassItemView;
import kg.musabaev.onlinetutorback.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Primary
public class SimpleClassService implements ClassService {

	private final ClassMapper classMapper;
	private final GroupClassRepo groupClassRepo;
	private final IndividualClassRepo individualClassRepo;

	@Override
	public ResponseEntity<NewClassResponse> createGroupClass(NewGroupClassRequest dto) {
		throwConflictIf(() -> groupClassRepo.existsByTitle(dto.getTitle()));

		var savedClass = groupClassRepo.save(classMapper.toModel(dto));
		return ResponseEntity.ok(classMapper.toDto(savedClass));
	}

	@Override
	public ResponseEntity<NewClassResponse> createIndividualClass(NewIndividualClassRequest dto) {
		throwConflictIf(() -> individualClassRepo.existsByTitle(dto.getTitle()));

		var savedClass = individualClassRepo.save(classMapper.toModel(dto));
		return ResponseEntity.ok(classMapper.toDto(savedClass));
	}

	@Override
	public ResponseEntity<Void> updateGroupClass(Long id, UpdateGroupClassRequest dto) {
		throwConflictIf(() -> groupClassRepo.existsByTitleAndIgnoringById(dto.getTitle(), id));

		Optional<GroupClass> persistedClass = groupClassRepo.findById(id);
		persistedClass.ifPresent(c -> {
			classMapper.update(dto, c);
			groupClassRepo.save(c);
		});
		persistedClass.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> updateIndividualClass(Long id, UpdateIndividualClassRequest dto) {
		throwConflictIf(() -> individualClassRepo.existsByTitleAndIgnoringById(dto.getTitle(), id));

		Optional<IndividualClass> persistedClass = individualClassRepo.findById(id);
		persistedClass.ifPresent(c -> {
			classMapper.update(dto, c);
			individualClassRepo.save(c);
		});
		persistedClass.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Page<GroupClassItemView>> getAllGroupClasses(Pageable pageable) {
		return ResponseEntity.ok(groupClassRepo.findAllProjectedBy(pageable));
	}

	@Override
	public ResponseEntity<Page<IndividualClassItemView>> getAllIndividualClasses(Pageable pageable) {
		return ResponseEntity.ok(individualClassRepo.findAllProjectedBy(pageable));
	}

	@Override
	public ResponseEntity<GroupClassItemView> getGroupClassById(long id) {
		var clazz = groupClassRepo.findProjectedById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
		return ResponseEntity.ok(clazz);
	}

	@Override
	public ResponseEntity<IndividualClassItemView> getIndividualClassById(long id) {
		var clazz = individualClassRepo.findProjectedById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
		return ResponseEntity.ok(clazz);
	}

	public void throwConflictIf(Supplier<Boolean> function) {
		if (function.get()) throw new ResponseStatusException(CONFLICT);
	}
}
