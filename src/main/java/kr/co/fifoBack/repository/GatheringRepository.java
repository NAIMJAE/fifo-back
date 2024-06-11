package kr.co.fifoBack.repository;

import kr.co.fifoBack.entity.gathering.Gathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Integer> {
}
