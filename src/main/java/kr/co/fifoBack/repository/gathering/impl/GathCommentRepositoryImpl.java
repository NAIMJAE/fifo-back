package kr.co.fifoBack.repository.gathering.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.entity.QUsers;
import kr.co.fifoBack.entity.gathering.QGathComment;
import kr.co.fifoBack.repository.gathering.custom.GathCommentRepositoryCustom;
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
public class GathCommentRepositoryImpl implements GathCommentRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QGathComment qGathComment = QGathComment.gathComment;
    private final QUsers qUsers = QUsers.users;
    // 댓글 불러오기
    @Override
    public Page<Tuple> selectCommentByGathno(PageRequestDTO pageRequestDTO, Pageable pageable){

        QueryResults<Tuple> results = jpaQueryFactory
                .select(qGathComment, qUsers.nick, qUsers.thumb)
                .from(qGathComment)
                .join(qUsers).on(qGathComment.userno.eq(qUsers.userno))
                .where(qGathComment.gathno.eq(pageRequestDTO.getGathno()))
                .orderBy(qGathComment.commentno.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Tuple> contents = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(contents, pageable, total);
    }
}
