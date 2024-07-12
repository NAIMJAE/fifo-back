package kr.co.fifoBack.repository.gathering;

import kr.co.fifoBack.entity.gathering.Gathering;
import kr.co.fifoBack.entity.gathering.Recruit;
import kr.co.fifoBack.repository.gathering.custom.GatheringRepositoryCustom;
import kr.co.fifoBack.repository.gathering.custom.RecruitRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecruitRepository extends JpaRepository<Recruit, Integer>, RecruitRepositoryCustom {
    // 모임 신청 전 이미 신청한 사람인지 조회
    public Optional<Recruit> findByUsernoAndGathno(int userno, int gathno);
}
