package kg.musabaev.onlinetutorback.repository;

import kg.musabaev.onlinetutorback.model.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialistRepo extends JpaRepository<Specialist, Long> {
}
