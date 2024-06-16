package kr.co.fifoBack.repository.gathering.custom;

import com.querydsl.core.Tuple;
import kr.co.fifoBack.dto.PageRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface GatheringRepositoryCustom {

    // 모임글 목록 전체 조회
    public Page<Tuple> selectGatherings(PageRequestDTO pageRequestDTO, Pageable pageable);
    // 모임글 목록 검색 조회
    public Page<Tuple> selectGatheringsByKeyword(PageRequestDTO pageRequestDTO, Pageable pageable);

    // 모임 글 보기
    public Tuple selectGathering(int gathno);
}
