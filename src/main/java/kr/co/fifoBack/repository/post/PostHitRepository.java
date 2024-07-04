package kr.co.fifoBack.repository.post;

import kr.co.fifoBack.entity.post.PostHit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostHitRepository extends JpaRepository<PostHit, Integer> {
    // 조회수 체크
    public Optional<PostHit> findByPnoAndAddress(int pno, String address);
}
