package kr.co.fifoBack.repository.gathering.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.entity.QUser;
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
    private final QUser qUser = QUser.user;

    // 모임글 목록 전체 조회
    @Override
    public Page<Tuple> selectGatherings(PageRequestDTO pageRequestDTO, Pageable pageable) {

        QueryResults<Tuple> result = jpaQueryFactory
                .select(qGathering, qUser.name, qUser.thumb)
                .from(qGathering)
                .join(qUser)
                .on(qGathering.userno.eq(qUser.userNo))
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
                .select(qGathering, qUser.name, qUser.thumb)
                .from(qGathering)
                .join(qUser)
                .on(qGathering.userno.eq(qUser.userNo))
                //.where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        log.info("result : " + result);

        List<Tuple> gathList = result.getResults();
        int total = (int) result.getTotal();

        return new PageImpl<>(gathList, pageable, total);
    }
}
