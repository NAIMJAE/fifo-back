package kr.co.fifoBack.repository.post.custom;

import com.querydsl.core.Tuple;
import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.dto.post.CommentDTO;
import kr.co.fifoBack.entity.post.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepositoryCustom {
    // 댓글 불러오기
    public Page<Tuple> selectCommentByPno(PageRequestDTO pageRequestDTO, Pageable pageable);
    // 답글 불러오기
    public List<CommentDTO> selectReply(int pno, int cno);
}
