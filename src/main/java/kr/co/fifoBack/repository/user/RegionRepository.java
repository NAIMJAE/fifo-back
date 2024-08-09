package kr.co.fifoBack.repository.user;

import kr.co.fifoBack.dto.user.RegionDTO;
import kr.co.fifoBack.entity.user.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Integer> {
}
