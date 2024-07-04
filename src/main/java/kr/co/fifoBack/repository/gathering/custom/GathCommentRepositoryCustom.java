package kr.co.fifoBack.repository.gathering.custom;

import com.querydsl.core.Tuple;
import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.dto.gathering.page.GathPageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface GathCommentRepositoryCustom {

    // 댓글 불러오기
    public Page<Tuple> selectCommentByGathno(PageRequestDTO pageRequestDTO, Pageable pageable);
}
