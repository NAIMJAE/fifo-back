package kr.co.fifoBack.repository.grade;

import kr.co.fifoBack.entity.grade.Question;
import kr.co.fifoBack.entity.grade.QuestionIOData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionIODataRepository extends JpaRepository<QuestionIOData, Integer> {
    public List<QuestionIOData> findByQuestionno(int questionno);
}
