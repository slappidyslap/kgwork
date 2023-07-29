package kg.musabaev.onlinetutorback.repository;

import kg.musabaev.onlinetutorback.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
}
