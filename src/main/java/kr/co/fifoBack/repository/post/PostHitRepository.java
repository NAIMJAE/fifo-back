package kr.co.fifoBack.repository.post;

import kr.co.fifoBack.entity.post.PostHit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostHitRepository extends JpaRepository<PostHit, Integer> {
    // 조회수 체크
    public Optional<PostHit> findByPnoAndAddress(int pno, String address);

    // 모임글 조회수 체크
    public Optional<PostHit> findByGathnoAndAddress(int pno, String address);

    // 게시글 삭제시 조회수 보관 삭제
    public void deleteByPno(int pno);
}
