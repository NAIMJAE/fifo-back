package kr.co.fifoBack.repository.post.custom;

import com.querydsl.core.Tuple;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepositoryCustom {
    // 댓글 불러오기
    public List<Tuple> selectCommentByPno(int pno);
}
