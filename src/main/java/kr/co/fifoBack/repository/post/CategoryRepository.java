package kr.co.fifoBack.repository.post;

import kr.co.fifoBack.entity.post.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
