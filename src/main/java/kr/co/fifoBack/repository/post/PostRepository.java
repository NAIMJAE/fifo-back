package kr.co.fifoBack.repository.post;

import kr.co.fifoBack.entity.post.Post;
import kr.co.fifoBack.repository.post.custom.PostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>, PostRepositoryCustom {
    // 게시글 조회수 +1 프로시저 정의
    @Procedure(name = "incrementposthit")
    void incrementPostHit(@Param("pno") int pno);
}
