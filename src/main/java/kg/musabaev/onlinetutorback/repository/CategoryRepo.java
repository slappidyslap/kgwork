package kg.musabaev.onlinetutorback.repository;

import kg.musabaev.onlinetutorback.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

	List<Category> findAllByIdIn(Collection<Integer> ids);
}
