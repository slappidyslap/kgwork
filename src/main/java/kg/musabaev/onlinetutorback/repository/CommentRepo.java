package kg.musabaev.onlinetutorback.repository;

import kg.musabaev.onlinetutorback.model.Comment;
import kg.musabaev.onlinetutorback.repository.projection.CommentItemView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

	Page<CommentItemView> findAllProjectedByBaseClassId(long id, Pageable pageable);
}
