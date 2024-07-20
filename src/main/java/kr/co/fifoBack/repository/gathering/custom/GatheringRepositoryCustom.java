package kr.co.fifoBack.repository.gathering.custom;

import com.querydsl.core.Tuple;
import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.dto.gathering.page.GathPageRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GatheringRepositoryCustom {

    // 모임글 목록 전체 조회
    public Page<Tuple> selectGatherings(GathPageRequestDTO pageRequestDTO, Pageable pageable);
    // 모임글 목록 검색 조회
    public Page<Tuple> selectGatheringsByKeyword(GathPageRequestDTO pageRequestDTO, Pageable pageable);

}
