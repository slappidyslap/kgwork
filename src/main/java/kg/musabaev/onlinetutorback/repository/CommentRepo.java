package kg.musabaev.onlinetutorback.repository;

import kg.musabaev.onlinetutorback.model.Comment;
import kg.musabaev.onlinetutorback.repository.projection.CommentItemView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

	Page<CommentItemView> findAllProjectedByBaseClassId(long id, Pageable pageable);

	@Query(value = "SELECT u.email FROM comments c LEFT JOIN users u ON c.author_id = u.id WHERE c.id = :id", nativeQuery = true)
	Optional<String> findAuthorEmailByCommentId(Long id);

	void deleteAllByBaseClassId(Long id);

	void deleteAllByAuthorId(Long id);

	@Modifying
	@Query(value = """
			DELETE FROM comments r WHERE r.id IN (
				SELECT c.id
				FROM comments c
				JOIN base_classes bc ON bc.id = c.base_class_id
				JOIN users u ON u.id = bc.author_id
				WHERE u.id = :id
			)
			""", nativeQuery = true)
	void deleteAllCommentsOfClassesCreatedByAuthorId(Long id);
}
