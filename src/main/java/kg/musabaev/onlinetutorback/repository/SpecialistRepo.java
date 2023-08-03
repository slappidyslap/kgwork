package kg.musabaev.onlinetutorback.repository;

import jakarta.persistence.Tuple;
import kg.musabaev.onlinetutorback.model.Specialist;
import kg.musabaev.onlinetutorback.repository.projection.SpecialistItemView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialistRepo extends JpaRepository<Specialist, Long> {

	Optional<SpecialistItemView> findProjectedById(Long id);

	@Query(value = "SELECT reputation, ratings_number FROM specialists WHERE id = :id", nativeQuery = true)
	Optional<Tuple> findReputationAndNumberRatingsOfSpecialist(Long id);

	@Modifying
	@Query(value = "UPDATE specialists SET reputation = :newReputation, ratings_number = ratings_number + 1 WHERE id = :id", nativeQuery = true)
	void updateReputation(Long id, Double newReputation);
}
