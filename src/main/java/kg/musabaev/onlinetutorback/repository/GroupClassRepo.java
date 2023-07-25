package kg.musabaev.onlinetutorback.repository;

import kg.musabaev.onlinetutorback.model.GroupClass;
import kg.musabaev.onlinetutorback.repository.projection.GroupClassItemView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GroupClassRepo extends JpaRepository<GroupClass, Long> {

	boolean existsByTitle(String title);

	boolean existsByTitleAndIdNot(String title, Long id);

	Page<GroupClassItemView> findAllProjectedBy(Pageable pageable);

	Optional<GroupClassItemView> findProjectedById(long id);
}
