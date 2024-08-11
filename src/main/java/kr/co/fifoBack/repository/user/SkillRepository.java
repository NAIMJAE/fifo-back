package kr.co.fifoBack.repository.user;

import kr.co.fifoBack.entity.user.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    List<Skill> findByUserno(int userno);
    Optional<Skill> findByUsernoAndLanguagename(int userNo, String LanguageName);
}
