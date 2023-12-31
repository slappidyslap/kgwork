package kg.musabaev.onlinetutorback.repository;

import kg.musabaev.onlinetutorback.model.Student;
import kg.musabaev.onlinetutorback.repository.projection.BaseClassItemView;
import kg.musabaev.onlinetutorback.repository.projection.StudentItemView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

	Optional<StudentItemView> findProjectedById(long id);

	@Query("SELECT u.finishedClasses FROM Student u WHERE u.id = :id")
	Page<BaseClassItemView> findAllFinishedClasses(Long id, Pageable pageable);

	@Query("SELECT u.inProcessClasses FROM Student u WHERE u.id = :id")
	Page<BaseClassItemView> findAllInProcessClassesClasses(Long id, Pageable pageable);

	@Query(value = "SELECT count(*) > 0 FROM students_rated_specialists WHERE student_id = :studentId AND specialist_id = :specialistId", nativeQuery = true)
	boolean existsInStudentsRatedSpecialists(long studentId, long specialistId);

	@Modifying
	@Query(value = "INSERT INTO students_rated_specialists (student_id, specialist_id) VALUES (:studentId, :specialistId)", nativeQuery = true)
	void saveStudentRatedSpecialist(long studentId, long specialistId);
}
