package kr.co.fifoBack.repository.post;

import kr.co.fifoBack.entity.post.CommentHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentHeartRepository extends JpaRepository<CommentHeart, Integer> {
    // 해당 게시글에 이미 좋아요를 눌렀는지 조회
    public Optional<CommentHeart> findByUserNoAndCno(int userNo, int cno);
}
