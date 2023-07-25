package kg.musabaev.onlinetutorback.repository;

import kg.musabaev.onlinetutorback.model.IndividualClass;
import kg.musabaev.onlinetutorback.repository.projection.IndividualClassItemView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualClassRepo extends JpaRepository<IndividualClass, Long> {

	boolean existsByTitle(String title);

	@Query(value = "SELECT count(title) > 0 FROM individual_classes WHERE title = :title AND id != :id", nativeQuery = true)
	boolean existsByTitleAndIgnoringById(@Param("title") String title, @Param("id") Long id);

	Page<IndividualClassItemView> findAllProjectedBy(Pageable pageable);
}
