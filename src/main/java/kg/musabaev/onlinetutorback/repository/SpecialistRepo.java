package kg.musabaev.onlinetutorback.repository;

import kg.musabaev.onlinetutorback.model.Specialist;
import kg.musabaev.onlinetutorback.repository.projection.SpecialistItemView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialistRepo extends JpaRepository<Specialist, Long> {

	Optional<SpecialistItemView> findProjectedById(Long id);
}
