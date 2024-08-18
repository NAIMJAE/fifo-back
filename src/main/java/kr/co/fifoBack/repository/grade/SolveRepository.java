package kr.co.fifoBack.repository.grade;

import kr.co.fifoBack.entity.grade.Solve;
import kr.co.fifoBack.repository.grade.custom.SolveRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolveRepository extends JpaRepository<Solve, String>, SolveRepositoryCustom {
    public List<Solve> findByQuestionnoAndUsernoOrderBySolveddate(int questionNo, int userNo);

}
