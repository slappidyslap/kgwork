package kg.musabaev.onlinetutorback.repository;

import kg.musabaev.onlinetutorback.model.GroupClass;
import kg.musabaev.onlinetutorback.repository.projection.GroupClassItemView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GroupClassRepo extends JpaRepository<GroupClass, Long> {

	String queryProjected = """
			SELECT
			c.id AS id,
			c.title AS title,
			c.description AS description,
			c.price AS price,
			c.createdDate AS createdDate,
			c.category AS category,
			c.coverUrl AS coverUrl,
			c.startDateTime AS startDateTime,
			c.finishDateTime AS finishDateTime,
			'group_class' AS classType
			FROM GroupClass c
			""";

	boolean existsByTitle(String title);

	boolean existsByTitleAndIdNot(String title, Long id);

	@Query(queryProjected)
	Page<GroupClassItemView> findAllProjectedBy(Pageable pageable);

	@Query(queryProjected + " WHERE c.id = :id")
	Optional<GroupClassItemView> findProjectedById(long id);

	@Query(queryProjected + "WHERE c.category.id = :id")
	Page<GroupClassItemView> findAllProjectedByCategoryId(long id, Pageable pageable);
}
