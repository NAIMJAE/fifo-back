package kr.co.fifoBack.repository.user;

import kr.co.fifoBack.entity.user.UserRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRegionRepository extends JpaRepository<UserRegion, Integer> {
    public List<UserRegion> findByUserno(int userno);
}

