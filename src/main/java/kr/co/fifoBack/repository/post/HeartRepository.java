package kr.co.fifoBack.repository.post;

import kr.co.fifoBack.entity.post.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Integer> {
    // 해당 게시글에 이미 좋아요를 눌렀는지 조회
    public Optional<Heart> findByUserNoAndPno(int userNo, int pno);
}
