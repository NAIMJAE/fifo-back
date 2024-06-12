package kr.co.fifoBack.repository.post;

import kr.co.fifoBack.entity.post.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
}
