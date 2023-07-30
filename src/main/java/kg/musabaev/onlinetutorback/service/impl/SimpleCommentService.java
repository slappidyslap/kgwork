package kg.musabaev.onlinetutorback.service.impl;

import kg.musabaev.onlinetutorback.dto.request.NewCommentRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateCommentRequest;
import kg.musabaev.onlinetutorback.dto.response.NewCommentResponse;
import kg.musabaev.onlinetutorback.exception.ClassNotFoundException;
import kg.musabaev.onlinetutorback.exception.CommentNotFoundException;
import kg.musabaev.onlinetutorback.mapper.CommentMapper;
import kg.musabaev.onlinetutorback.model.Comment;
import kg.musabaev.onlinetutorback.model.Student;
import kg.musabaev.onlinetutorback.repository.BaseClassRepo;
import kg.musabaev.onlinetutorback.repository.CommentRepo;
import kg.musabaev.onlinetutorback.repository.projection.CommentItemView;
import kg.musabaev.onlinetutorback.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Supplier;

@Service
@Primary
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimpleCommentService implements CommentService {

	CommentRepo commentRepo;
	BaseClassRepo baseClassRepo;
	CommentMapper commentMapper;


	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<Page<CommentItemView>> getAllCommentsOfClass(long classId, Pageable pageable) {
		return ResponseEntity.ok(commentRepo.findAllProjectedByBaseClassId(classId, pageable));
	}

	@Override
	@Transactional
	public ResponseEntity<NewCommentResponse> createComment(NewCommentRequest dto) {
		throwIf(new ClassNotFoundException(), () -> !baseClassRepo.existsById(dto.classId()));

		var comment = commentMapper.toModel(dto);
		comment.setBaseClass(baseClassRepo.getReferenceById(dto.classId()));
		comment.setAuthor(getAuthenticatedUser());

		var savedComment = commentRepo.save(comment);
		return new ResponseEntity<>(commentMapper.toDto(savedComment), HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<Void> updateComment(long id, UpdateCommentRequest dto) {

		Optional<Comment> comment = commentRepo.findById(id);
		comment.ifPresent(c -> {
			commentMapper.update(dto, c);
			commentRepo.save(c);
		});
		comment.orElseThrow(CommentNotFoundException::new);
		return ResponseEntity.noContent().build();
	}

	@Override
	@Transactional
	public ResponseEntity<Void> deleteComment(long id) {
		throwIf(new CommentNotFoundException(), () -> !commentRepo.existsById(id));

		commentRepo.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	public void throwIf(RuntimeException e, Supplier<Boolean> function) {
		if (function.get()) throw e;
	}

	public Student getAuthenticatedUser() {
		return ((Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}
}
