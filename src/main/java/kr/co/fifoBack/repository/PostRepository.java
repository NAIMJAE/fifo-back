package kr.co.fifoBack.repository;

import kr.co.fifoBack.entity.Post;
import kr.co.fifoBack.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
