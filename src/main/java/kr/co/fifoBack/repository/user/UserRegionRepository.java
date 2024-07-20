package kr.co.fifoBack.repository.user;

import kr.co.fifoBack.entity.user.UserRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRegionRepository extends JpaRepository<UserRegion, Integer> {
    public List<UserRegion> findByUserno(int userno);
}
