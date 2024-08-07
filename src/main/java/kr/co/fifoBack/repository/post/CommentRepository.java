package kr.co.fifoBack.repository.post;

import kr.co.fifoBack.entity.post.Comment;
import kr.co.fifoBack.repository.post.custom.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>, CommentRepositoryCustom {
    // 게시글 삭제시 댓글 삭제
    public void deleteByPno(int pno);
}
