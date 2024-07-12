package kr.co.fifoBack.repository.gathering.custom;

import com.querydsl.core.Tuple;
import kr.co.fifoBack.entity.gathering.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecruitRepositoryCustom {
    // 모임 신청 목록 조회
    public List<Tuple> selectRecruitList(int gathno);
}
