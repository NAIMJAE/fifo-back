package kr.co.fifoBack.repository.gathering.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.entity.QUsers;
import kr.co.fifoBack.entity.gathering.QGathering;
import kr.co.fifoBack.repository.gathering.custom.GatheringRepositoryCustom;
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
public class GatheringRepositoryImpl implements GatheringRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private final QGathering qGathering = QGathering.gathering;
    private final QUsers qUser = QUsers.users;

    // 모임글 목록 전체 조회
    @Override
    public Page<Tuple> selectGatherings(PageRequestDTO pageRequestDTO, Pageable pageable) {

        QueryResults<Tuple> result = jpaQueryFactory
                .select(qGathering, qUser.name, qUser.thumb)
                .from(qGathering)
                .join(qUser)
                .on(qGathering.userno.eq(qUser.userno))
                .orderBy(qGathering.gathno.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        log.info("result : " + result);

        List<Tuple> gathList = result.getResults();
        int total = (int) result.getTotal();

        return new PageImpl<>(gathList, pageable, total);
    }
    // 모임글 목록 검색 조회
    @Override
    public Page<Tuple> selectGatheringsByKeyword(PageRequestDTO pageRequestDTO, Pageable pageable) {

        BooleanExpression expression = null;

        QueryResults<Tuple> result = jpaQueryFactory
                .select(qGathering, qUser.nick, qUser.thumb)
                .from(qGathering)
                .join(qUser)
                .on(qGathering.userno.eq(qUser.userno))
                //.where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        log.info("result : " + result);

        List<Tuple> gathList = result.getResults();
        int total = (int) result.getTotal();

        return new PageImpl<>(gathList, pageable, total);
    }
    // 모임 글 보기
    @Override
    public Tuple selectGathering(int gathno){
        return jpaQueryFactory
                .select(qGathering, qUser.nick, qUser.thumb)
                .from(qGathering)
                .join(qUser)
                .on(qGathering.userno.eq(qUser.userno))
                .where(qGathering.gathno.eq(gathno))
                .fetchOne();
    }
}
