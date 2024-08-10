package kr.co.fifoBack.repository.mooim;

import kr.co.fifoBack.entity.gathering.Mooim;
import kr.co.fifoBack.repository.mooim.custom.MooimRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MooimRepository extends JpaRepository<Mooim, Integer> , MooimRepositoryCustom {
    // 모임글 목록 상태 조회
    public List<Mooim> findMooimsByUsernoAndMooimstate(int userno, int mooimstate);
}
