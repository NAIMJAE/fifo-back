package kr.co.fifoBack.repository.grade.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.fifoBack.entity.grade.QQuestion;
import kr.co.fifoBack.entity.grade.QSolve;
import kr.co.fifoBack.entity.user.QSkill;
import kr.co.fifoBack.repository.grade.custom.SolveRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SolveRepositoryImpl implements SolveRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QSolve qSolve=  QSolve.solve;
    private final QQuestion qQuestion = QQuestion.question;

    public List<Tuple> getUserGradeInfo(int userNo) {
        QueryResults<Tuple> result = jpaQueryFactory
                .select(qSolve, qQuestion)
                .from(qSolve)
                .join(qQuestion)
                .on(qSolve.questionno.eq(qQuestion.questionno))
                .where(qSolve.solved.eq("100").and(qSolve.userno.eq(userNo)))
                .orderBy(qSolve.solveddate.desc())
                .limit(10)
                .fetchResults();

        List<Tuple> UserGradeInfo = result.getResults();
        return UserGradeInfo;
    }
}
