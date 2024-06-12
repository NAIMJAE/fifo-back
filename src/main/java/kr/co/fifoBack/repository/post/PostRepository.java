package kr.co.fifoBack.repository.post;

import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.entity.post.Post;
import kr.co.fifoBack.repository.post.custom.PostRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>, PostRepositoryCustom {
}
