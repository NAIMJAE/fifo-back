package kr.co.fifoBack.repository.user;

import kr.co.fifoBack.entity.user.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Integer> {
}
