package kg.musabaev.onlinetutorback.repository;

import kg.musabaev.onlinetutorback.model.BaseClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseClassRepo extends JpaRepository<BaseClass, Long> {

    @Query(value = "SELECT u.email FROM base_classes c LEFT JOIN users u ON c.author_id = u.id WHERE c.id = :id", nativeQuery = true)
    Optional<String> findAuthorEmailByClassId(Long id);

    void deleteAllByAuthorId(long id);
}