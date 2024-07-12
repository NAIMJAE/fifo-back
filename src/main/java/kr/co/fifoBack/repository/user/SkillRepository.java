package kr.co.fifoBack.repository.user;

import kr.co.fifoBack.entity.user.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
}
