package kr.co.fifoBack.repository.gathering;

import kr.co.fifoBack.entity.gathering.Gathering;
import kr.co.fifoBack.repository.gathering.custom.GatheringRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Integer>, GatheringRepositoryCustom {
}
