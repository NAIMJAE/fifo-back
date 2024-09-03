package kr.co.fifoBack.repository.grade;

import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.entity.grade.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    public List<Question> findAllByLanguagename(String language);

    public Page<Question> findByQuestionno(int questionNo, Pageable pageable);

    public Page<Question> findByTitleContaining(String title, Pageable pageable);

    public Page<Question> findByLevel(int level, Pageable pageable);

}
