package kr.co.fifoBack.repository.post.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.fifoBack.entity.QUsers;
import kr.co.fifoBack.entity.post.Comment;
import kr.co.fifoBack.entity.post.QComment;
import kr.co.fifoBack.repository.post.custom.CommentRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QComment qComment = QComment.comment;
    private final QUsers qUsers = QUsers.users;

    // 댓글 불러오기
    public List<Tuple> selectCommentByPno(int pno) {
        QueryResults<Tuple> commentTuple = jpaQueryFactory
                .select(qComment, qUsers.thumb, qUsers.nick)
                .from(qComment)
                .join(qUsers)
                .on(qComment.userNo.eq(qUsers.userno))
                .where(qComment.pno.eq(pno))
                .orderBy(qComment.cno.desc())
                .fetchResults();

        return commentTuple.getResults();
    }
}
