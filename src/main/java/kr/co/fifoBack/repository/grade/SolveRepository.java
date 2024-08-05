package kr.co.fifoBack.repository.grade;

import kr.co.fifoBack.entity.grade.Solve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolveRepository extends JpaRepository<Solve, String> {
    public List<Solve> findByQuestionnoAndUserno(int questionNo, int userNo);
}
