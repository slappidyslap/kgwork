package kg.musabaev.onlinetutorback.repository;

import kg.musabaev.onlinetutorback.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
