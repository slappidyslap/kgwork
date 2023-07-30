package kg.musabaev.onlinetutorback.service;

import kg.musabaev.onlinetutorback.dto.request.NewCommentRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateCommentRequest;
import kg.musabaev.onlinetutorback.dto.response.NewCommentResponse;
import kg.musabaev.onlinetutorback.repository.projection.CommentItemView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CommentService {

	ResponseEntity<Page<CommentItemView>> getAllCommentsOfClass(long classId, Pageable pageable);

	ResponseEntity<NewCommentResponse> createComment(NewCommentRequest dto);

	ResponseEntity<Void> updateComment(long id, UpdateCommentRequest dto);

	ResponseEntity<Void> deleteComment(long id);
}
