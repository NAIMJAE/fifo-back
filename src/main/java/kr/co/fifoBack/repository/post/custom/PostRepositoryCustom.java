package kr.co.fifoBack.repository.post.custom;

import kr.co.fifoBack.dto.PageRequestDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositoryCustom {
    // 게시글 조회 + 검색
    public void selectPostByKeyword(PageRequestDTO pageRequestDTO, Pageable pageable);
}
