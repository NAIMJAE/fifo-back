package kr.co.fifoBack.repository.post.custom;

import com.querydsl.core.Tuple;
import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.entity.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepositoryCustom {
    // 게시글 조회 + 검색
    public Page<Tuple> selectPostByKeyword(PageRequestDTO pageRequestDTO, Pageable pageable);
    // 게시글 태그 조회
    public List<String> selectTagForPno(int pno);
    // 게시글 보기
    public Tuple selectPost(int pno);
}
