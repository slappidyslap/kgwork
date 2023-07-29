package kg.musabaev.onlinetutorback.service.impl;

import kg.musabaev.onlinetutorback.dto.request.NewGroupClassRequest;
import kg.musabaev.onlinetutorback.dto.request.NewIndividualClassRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateGroupClassRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateIndividualClassRequest;
import kg.musabaev.onlinetutorback.dto.response.NewClassResponse;
import kg.musabaev.onlinetutorback.mapper.ClassMapper;
import kg.musabaev.onlinetutorback.model.Category;
import kg.musabaev.onlinetutorback.model.GroupClass;
import kg.musabaev.onlinetutorback.model.IndividualClass;
import kg.musabaev.onlinetutorback.repository.CategoryRepo;
import kg.musabaev.onlinetutorback.repository.GroupClassRepo;
import kg.musabaev.onlinetutorback.repository.IndividualClassRepo;
import kg.musabaev.onlinetutorback.repository.projection.GroupClassItemView;
import kg.musabaev.onlinetutorback.repository.projection.IndividualClassItemView;
import kg.musabaev.onlinetutorback.service.ClassService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Primary
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimpleClassService implements ClassService {

	ClassMapper classMapper;
	GroupClassRepo groupClassRepo;
	IndividualClassRepo individualClassRepo;
	CategoryRepo categoryRepo;

	@Override
	@Transactional
	public ResponseEntity<NewClassResponse> createGroupClass(NewGroupClassRequest dto) {
		throwConflictIf(() -> groupClassRepo.existsByTitle(dto.getTitle()));
		throwConflictIf(() -> individualClassRepo.existsByTitle(dto.getTitle()));

		var clazz = classMapper.toModel(dto);
		Category category = categoryRepo.findById(dto.getCategoryId()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
		clazz.setCategory(category);
		var savedClass = groupClassRepo.save(clazz);
		return new ResponseEntity<>(classMapper.toDto(savedClass), HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<NewClassResponse> createIndividualClass(NewIndividualClassRequest dto) {
		throwConflictIf(() -> individualClassRepo.existsByTitle(dto.getTitle()));
		throwConflictIf(() -> groupClassRepo.existsByTitle(dto.getTitle()));

		var clazz = classMapper.toModel(dto);
		Category category = categoryRepo.findById(dto.getCategoryId()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
		clazz.setCategory(category);
		var savedClass = individualClassRepo.save(clazz);
		return new ResponseEntity<>(classMapper.toDto(savedClass), HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<Void> updateGroupClass(Long id, UpdateGroupClassRequest dto) {
		throwConflictIf(() -> groupClassRepo.existsByTitleAndIdNot(dto.getTitle(), id));
		throwConflictIf(() -> individualClassRepo.existsByTitleAndIdNot(dto.getTitle(), id));

		Optional<GroupClass> persistedClass = groupClassRepo.findById(id);
		persistedClass.ifPresent(c -> {
			classMapper.update(dto, c);
			Category category = categoryRepo
					.findById(dto.getCategoryId())
					.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
			c.setCategory(category);
			groupClassRepo.save(c);
		});
		persistedClass.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
		return ResponseEntity.noContent().build();
	}

	@Override
	@Transactional
	public ResponseEntity<Void> updateIndividualClass(Long id, UpdateIndividualClassRequest dto) {
		throwConflictIf(() -> individualClassRepo.existsByTitleAndIdNot(dto.getTitle(), id));
		throwConflictIf(() -> groupClassRepo.existsByTitleAndIdNot(dto.getTitle(), id));

		Optional<IndividualClass> persistedClass = individualClassRepo.findById(id);
		persistedClass.ifPresent(c -> {
			classMapper.update(dto, c);
			Category category = categoryRepo
					.findById(dto.getCategoryId())
					.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
			c.setCategory(category);
			individualClassRepo.save(c);
		});
		persistedClass.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
		return ResponseEntity.noContent().build();
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<Page<GroupClassItemView>> getAllGroupClasses(long categoryId, Pageable pageable) {
		if (categoryId == 0)
			return ResponseEntity.ok(groupClassRepo.findAllProjectedBy(pageable));
		return ResponseEntity.ok(groupClassRepo.findAllProjectedByCategoryId(categoryId, pageable));
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<Page<IndividualClassItemView>> getAllIndividualClasses(long categoryId, Pageable pageable) {
		if (categoryId == 0)
			return ResponseEntity.ok(individualClassRepo.findAllProjectedBy(pageable));
		return ResponseEntity.ok(individualClassRepo.findAllProjectedByCategoryId(categoryId, pageable));
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<GroupClassItemView> getGroupClassById(long id) {
		var clazz = groupClassRepo.findProjectedById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
		return ResponseEntity.ok(clazz);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<IndividualClassItemView> getIndividualClassById(long id) {
		var clazz = individualClassRepo.findProjectedById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
		return ResponseEntity.ok(clazz);
	}

	public void throwConflictIf(Supplier<Boolean> function) {
		if (function.get()) throw new ResponseStatusException(CONFLICT);
	}
}
