package kr.co.fifoBack.repository.user;

import kr.co.fifoBack.entity.user.Experience;
import org.modelmapper.internal.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
    public List<Experience> findAllByUserno(int userno);
}
