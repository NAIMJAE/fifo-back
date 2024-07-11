package kr.co.fifoBack.repository.gathering.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.fifoBack.entity.QUsers;
import kr.co.fifoBack.entity.gathering.QRecruit;
import kr.co.fifoBack.entity.gathering.Recruit;
import kr.co.fifoBack.repository.gathering.custom.RecruitRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RecruitRepositoryImpl implements RecruitRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QRecruit qRecruit = QRecruit.recruit;
    private final QUsers qUsers = QUsers.users;

    // 모임 신청 목록 조회
    public List<Tuple> selectRecruitList(int gathno) {
        QueryResults<Tuple> result = jpaQueryFactory
                .select(qRecruit, qUsers)
                .from(qRecruit)
                .join(qUsers)
                .on(qRecruit.userno.eq(qUsers.userno))
                .where(qRecruit.gathno.eq(gathno))
                .fetchResults();

        // recruitno, userno, gathno, recruitState, nick, region, thumb
        List<Tuple> recruitList = result.getResults();
        return recruitList;
     }
}
