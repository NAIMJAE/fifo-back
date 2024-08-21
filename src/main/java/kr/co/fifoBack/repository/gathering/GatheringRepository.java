package kr.co.fifoBack.repository.gathering;

import com.querydsl.core.Tuple;
import kr.co.fifoBack.entity.gathering.Gathering;
import kr.co.fifoBack.repository.gathering.custom.GatheringRepositoryCustom;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Integer>, GatheringRepositoryCustom {

    // 모임 글 보기 + 조회수 업
    @Query(value = "select * from get_and_update_gathering(:ingathno)", nativeQuery = true)
    List<Object[]> selectGatheringAndHitUp(@Param("ingathno") int ingathno);

}
