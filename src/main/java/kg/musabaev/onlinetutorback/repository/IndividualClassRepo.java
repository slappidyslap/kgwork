package kg.musabaev.onlinetutorback.repository;

import kg.musabaev.onlinetutorback.model.IndividualClass;
import kg.musabaev.onlinetutorback.repository.projection.IndividualClassItemView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndividualClassRepo extends JpaRepository<IndividualClass, Long> {

	boolean existsByTitle(String title);

	boolean existsByTitleAndIdNot(String title, Long id);

	Page<IndividualClassItemView> findAllProjectedBy(Pageable pageable);

	Optional<IndividualClassItemView> findProjectedById(long id);
}
