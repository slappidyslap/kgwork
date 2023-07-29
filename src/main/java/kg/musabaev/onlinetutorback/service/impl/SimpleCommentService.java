package kg.musabaev.onlinetutorback.service.impl;

import kg.musabaev.onlinetutorback.dto.request.DeleteCommentRequest;
import kg.musabaev.onlinetutorback.dto.request.NewCommentRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateCommentRequest;
import kg.musabaev.onlinetutorback.dto.response.NewCommentResponse;
import kg.musabaev.onlinetutorback.exception.ClassNotFoundException;
import kg.musabaev.onlinetutorback.exception.CommentNotFoundException;
import kg.musabaev.onlinetutorback.mapper.CommentMapper;
import kg.musabaev.onlinetutorback.model.Comment;
import kg.musabaev.onlinetutorback.repository.BaseClassRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@Primary
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimpleCommentService implements CommentService {

	CommentRepo commentRepo;
	BaseClassRepository baseClassRepository;
	CommentMapper commentMapper;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<Page<CommentItemView>> getAllCommentsOfClass(long classId, Pageable pageable) {
		return ResponseEntity.ok(commentRepo.findAllProjectedByBaseClassId(classId, pageable));
	}

	@Override
	@Transactional
	public ResponseEntity<NewCommentResponse> createComment(NewCommentRequest dto) {
		throwIf(null /*new UserNotFoundException()*/, () -> false /*userRepo.existsById(dto.userId())*/); // FIXME
		throwIf(new ClassNotFoundException(), () -> !baseClassRepository.existsById(dto.classId()));

		var comment = commentMapper.toModel(dto);
		comment.setBaseClass(baseClassRepository.getReferenceById(dto.classId()));
		comment.setUserId(dto.userId());
//		comment.setUserId(userRepo.getReferenceById(dto.userId())); FIXME
		var savedComment = commentRepo.save(comment);
		return new ResponseEntity<>(commentMapper.toDto(comment), HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<Void> updateComment(long id, UpdateCommentRequest dto) {
		throwIf(null /*new UserNotFoundException()*/, () -> false /*userRepo.existsById(dto.userId())*/);

		Optional<Comment> comment = commentRepo.findById(id);
		comment.ifPresent(c -> {
			throwIf(new ResponseStatusException(UNAUTHORIZED), () -> !dto.userId().equals(c.getUserId())); // FIXME

			commentMapper.update(dto, c);
			commentRepo.save(c);
		});
		comment.orElseThrow(CommentNotFoundException::new);
		return ResponseEntity.noContent().build();
	}

	@Override
	@Transactional
	public ResponseEntity<Void> deleteComment(long id, DeleteCommentRequest dto) {
		throwIf(new CommentNotFoundException(), () -> !commentRepo.existsById(id));
		throwIf(new ResponseStatusException(UNAUTHORIZED), () -> id != dto.userId()); // FIXME

		commentRepo.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	public void throwIf(RuntimeException e, Supplier<Boolean> function) {
		if (function.get()) throw e;
	}
}
