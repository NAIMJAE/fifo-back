package kr.co.fifoBack.repository.gathering.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.fifoBack.entity.QUsers;
import kr.co.fifoBack.entity.gathering.QMooimMember;
import kr.co.fifoBack.repository.gathering.custom.MooimMemberRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MooimMemberRepositoryImpl implements MooimMemberRepositoryCustom {

     private final JPAQueryFactory jpaQueryFactory;
     private final QMooimMember qMooimMember = QMooimMember.mooimMember;
     private final QUsers qUsers = QUsers.users;

    // 모임 참가자 정보
    public List<Tuple> selectMooimMemberAndSkills(int mooimno) {

        return jpaQueryFactory
                .select(qMooimMember, qUsers)
                .from(qMooimMember)
                .join(qUsers).on(qMooimMember.userno.eq(qUsers.userno))
                .where(qMooimMember.mooimno.eq(mooimno))
                .fetch();
    }
}
