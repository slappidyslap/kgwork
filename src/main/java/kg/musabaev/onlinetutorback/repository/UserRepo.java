package kg.musabaev.onlinetutorback.repository;

import kg.musabaev.onlinetutorback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String login);

	boolean existsByEmail(String email);
}
