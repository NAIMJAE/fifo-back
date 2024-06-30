package kr.co.fifoBack.repository.post;

import kr.co.fifoBack.entity.post.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
    // 게시글 파일 목록 조회
    public List<File> findByPno(int pno);
}
