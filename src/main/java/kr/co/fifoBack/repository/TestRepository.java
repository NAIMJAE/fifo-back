package kr.co.fifoBack.repository;

import kr.co.fifoBack.entity.post.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, String> {
}
