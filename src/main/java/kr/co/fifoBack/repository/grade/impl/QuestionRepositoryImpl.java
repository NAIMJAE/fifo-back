package kr.co.fifoBack.repository.grade.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.dto.gathering.GatheringDTO;
import kr.co.fifoBack.dto.gathering.page.GathPageRequestDTO;
import kr.co.fifoBack.entity.QUsers;
import kr.co.fifoBack.entity.gathering.QGathering;
import kr.co.fifoBack.repository.gathering.custom.GatheringRepositoryCustom;
import kr.co.fifoBack.repository.grade.custom.QuestionRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Tuple> selectQuestions(PageRequestDTO pageRequestDTO, Pageable pageable) {



        return null;
    }
}
