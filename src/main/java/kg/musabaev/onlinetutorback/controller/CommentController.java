package kg.musabaev.onlinetutorback.controller;

import jakarta.validation.Valid;
import kg.musabaev.onlinetutorback.dto.request.DeleteCommentRequest;
import kg.musabaev.onlinetutorback.dto.request.NewCommentRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateCommentRequest;
import kg.musabaev.onlinetutorback.dto.response.NewCommentResponse;
import kg.musabaev.onlinetutorback.repository.projection.CommentItemView;
import kg.musabaev.onlinetutorback.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/classes")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {

	CommentService commentService;

	@GetMapping("/{classId}/comments")
	ResponseEntity<Page<CommentItemView>> getAllCommentsOfClass(@ParameterObject Pageable pageable, @PathVariable long classId) {
		return commentService.getAllCommentsOfClass(classId, pageable);
	}

	@PostMapping("/comments")
	ResponseEntity<NewCommentResponse> createComment(@Valid @RequestBody NewCommentRequest dto) {
		return commentService.createComment(dto);
	}

	@PatchMapping("/comments/{id}")
	ResponseEntity<Void> updateComment(@PathVariable long id, @Valid @RequestBody UpdateCommentRequest dto) {
		return commentService.updateComment(id, dto);
	}

	@DeleteMapping("/comments/{id}")
	ResponseEntity<Void> deleteComment(@PathVariable long id, @Valid @RequestBody DeleteCommentRequest dto) {
		return commentService.deleteComment(id, dto);
	}
}
