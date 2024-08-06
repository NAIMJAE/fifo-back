package kr.co.fifoBack.repository.mooim.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.fifoBack.entity.QUsers;
import kr.co.fifoBack.entity.grade.QLanguage;
import kr.co.fifoBack.entity.mooim.Chat;
import kr.co.fifoBack.entity.mooim.QChat;
import kr.co.fifoBack.entity.user.QSkill;
import kr.co.fifoBack.repository.mooim.custom.ChatRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QChat qChat = QChat.chat;
    private final QUsers qUsers = QUsers.users;

    // 채팅 불러오기
    public List<Tuple> findByMooimno(int mooimno) {
        QueryResults<Tuple> result = jpaQueryFactory
                .select(qChat, qUsers.nick, qUsers.thumb)
                .from(qChat)
                .join(qUsers)
                .on(qChat.userno.eq(qUsers.userno))
                .where(qChat.mooimno.eq(mooimno))
                .orderBy(qChat.chatno.asc())
                .fetchResults();

        return result.getResults();
    }
}
