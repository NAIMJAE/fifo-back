package kr.co.fifoBack.repository.mooim.custom;

import com.querydsl.core.Tuple;
import kr.co.fifoBack.dto.gathering.page.GathPageRequestDTO;
import kr.co.fifoBack.entity.gathering.Mooim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MooimRepositoryCustom {

    // 모임 목록 전체 조회
    public List<Mooim> findMooimsByUserno(int userno);

    // 모임글 목록 상태 조회
    public List<Mooim> findMooimsByUsernoAndMooimstate(int userno, int mooimstate);

}
