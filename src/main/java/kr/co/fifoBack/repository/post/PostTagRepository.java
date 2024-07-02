package kr.co.fifoBack.repository.post;

import kr.co.fifoBack.entity.post.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Integer> {
    // 기존 태그 조회
    public List<PostTag> findByPno(int pno);
    // 기존 태그 삭제
    public void deleteByPno(int pno);
}
