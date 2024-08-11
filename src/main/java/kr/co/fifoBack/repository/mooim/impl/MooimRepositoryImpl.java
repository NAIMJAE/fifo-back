package kr.co.fifoBack.repository.mooim.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.fifoBack.entity.QUsers;
import kr.co.fifoBack.entity.gathering.Mooim;
import kr.co.fifoBack.entity.gathering.QMooim;
import kr.co.fifoBack.entity.gathering.QMooimMember;
import kr.co.fifoBack.repository.mooim.custom.MooimRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MooimRepositoryImpl implements MooimRepositoryCustom {

     private final JPAQueryFactory jpaQueryFactory;
     private final QMooim qMooim = QMooim.mooim;
     private final QMooimMember qMooimMember = QMooimMember.mooimMember;
     private final QUsers qUsers = QUsers.users;

    @Override
    public List<Mooim> findMooimsByUserno(int userno) {
        List<Mooim> results = jpaQueryFactory
                .select(qMooim)
                .from(qMooim)
                .join(qMooimMember).on(qMooim.mooimno.eq(qMooimMember.mooimno)
                        .and(qMooimMember.userno.eq(userno)))
                .orderBy(qMooim.mooimno.desc())
                .fetch();

        return results;
    }

    @Override
    public List<Mooim> findMooimsByUsernoAndMooimstate(int userno, int mooimstate) {
        List<Mooim> results = jpaQueryFactory
                .select(qMooim)
                .from(qMooim)
                .join(qMooimMember).on(qMooim.mooimno.eq(qMooimMember.mooimno)
                        .and(qMooimMember.userno.eq(userno)))
                .where(qMooim.mooimstate.eq(mooimstate))
                .orderBy(qMooim.mooimno.desc())
                .fetch();

        return results;
    }
}
