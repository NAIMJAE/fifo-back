package kr.co.fifoBack.repository.grade.custom;

import com.querydsl.core.Tuple;
import kr.co.fifoBack.dto.PageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepositoryCustom {

    public Page<Tuple> selectQuestions(PageRequestDTO pageRequestDTO, Pageable pageable);

}