package kr.co.fifoBack.repository.gathering.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.fifoBack.dto.gathering.GatheringDTO;
import kr.co.fifoBack.dto.gathering.page.GathPageRequestDTO;
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

    // 모임글 조회
    @Override
    public Tuple selectGatheringAndWriter(int gathno) {
        return jpaQueryFactory
                .select(qGathering, qUser.nick, qUser.thumb)
                .from(qGathering)
                .join(qUser).on(qGathering.userno.eq(qUser.userno))
                .where(qGathering.gathno.eq(gathno))
                .fetchOne();
    }

    // 모임글 목록 전체 조회
    @Override
    public Page<Tuple> selectGatherings(GathPageRequestDTO pageRequestDTO, Pageable pageable) {
        log.info("전체 조회");
        QueryResults<Tuple> result = jpaQueryFactory
                .select(qGathering, qUser.nick, qUser.thumb)
                .from(qGathering)
                .join(qUser)
                .on(qGathering.userno.eq(qUser.userno))
                .orderBy(qGathering.gathno.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        log.info("result : " + result);

        List<Tuple> gathList = result.getResults();
        int total = (int) result.getTotal();

        return new PageImpl<>(gathList, pageable, total);
    }
    // 모임글 목록 모집중 조회
    @Override
    public Page<Tuple> selectGatheringsByState(GathPageRequestDTO pageRequestDTO, Pageable pageable) {
        log.info("모집중 조회");
        QueryResults<Tuple> result = jpaQueryFactory
                .select(qGathering, qUser.nick, qUser.thumb)
                .from(qGathering)
                .join(qUser)
                .on(qGathering.userno.eq(qUser.userno))
                .where(qGathering.gathstate.eq("모집중"))
                .orderBy(qGathering.gathno.desc())
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
    public Page<Tuple> selectGatheringsByKeyword(GathPageRequestDTO pageRequestDTO, Pageable pageable) {
        log.info("검색 조회");
        GatheringDTO gatheringDTO = pageRequestDTO.getGatheringDTO();
        BooleanExpression expression = null;

        if (gatheringDTO != null) {
            if (gatheringDTO.getGathcate() != 0) {
                expression = qGathering.gathcate.eq(gatheringDTO.getGathcate());
            }
            if (gatheringDTO.getGathmode() != null) {
                expression = expression != null ? expression.and(qGathering.gathmode.eq(gatheringDTO.getGathmode())) : qGathering.gathmode.eq(gatheringDTO.getGathmode());
            }
            if (gatheringDTO.getGathtotalmember() > 0) {
                expression = expression != null ? expression.and(qGathering.gathtotalmember.eq(gatheringDTO.getGathtotalmember())) : qGathering.gathtotalmember.eq(gatheringDTO.getGathtotalmember());
            }
            if (gatheringDTO.getGathrecruitfield() != null) {
                expression = expression != null ? expression.and(qGathering.gathrecruitfield.contains(gatheringDTO.getGathrecruitfield())) : qGathering.gathrecruitfield.contains(gatheringDTO.getGathrecruitfield());
            }
            if (gatheringDTO.getGathlanguage() != null) {
                expression = expression != null ? expression.and(qGathering.gathlanguage.contains(gatheringDTO.getGathlanguage())) : qGathering.gathlanguage.contains(gatheringDTO.getGathlanguage());
            }
        }
        // 모집중만 보기일 때
        if(pageRequestDTO.isGathState()){
            expression = expression.and(qGathering.gathstate.eq("모집중"));
        }

        QueryResults<Tuple> result = jpaQueryFactory
                .select(qGathering, qUser.nick, qUser.thumb)
                .from(qGathering)
                .join(qUser)
                .on(qGathering.userno.eq(qUser.userno))
                .where(expression)
                .orderBy(qGathering.gathno.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        log.info("result : " + result);

        List<Tuple> gathList = result.getResults();
        int total = (int) result.getTotal();

        return new PageImpl<>(gathList, pageable, total);
    }

    // 모임 글 보기
    //
    public Tuple selectGathering(int gathno) {
        return jpaQueryFactory
                .select(qGathering, qUser.nick, qUser.thumb)
                .from(qGathering)
                .join(qUser)
                .on(qGathering.userno.eq(qUser.userno))
                .where(qGathering.gathno.eq(gathno))
                .fetchOne();
    }
}
