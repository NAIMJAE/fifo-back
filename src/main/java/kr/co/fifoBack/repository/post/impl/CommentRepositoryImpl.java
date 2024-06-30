package kr.co.fifoBack.repository.post.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.dto.post.CommentDTO;
import kr.co.fifoBack.entity.QUsers;
import kr.co.fifoBack.entity.post.Comment;
import kr.co.fifoBack.entity.post.QComment;
import kr.co.fifoBack.repository.post.custom.CommentRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QComment qComment = QComment.comment;
    private final QUsers qUsers = QUsers.users;
    private final ModelMapper modelMapper;

    // 댓글 불러오기
    public Page<Tuple> selectCommentByPno(PageRequestDTO pageRequestDTO, Pageable pageable) {
        QueryResults<Tuple> commentTuple = jpaQueryFactory
                .select(qComment, qUsers.thumb, qUsers.nick)
                .from(qComment)
                .join(qUsers)
                .on(qComment.userNo.eq(qUsers.userno))
                .where(qComment.pno.eq(pageRequestDTO.getPno()).and(qComment.parentCno.eq(0)))
                .orderBy(qComment.cno.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Tuple> content = commentTuple.getResults();
        long total = commentTuple.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    // 답글 불러오기
    public List<CommentDTO> selectReply(int pno, int cno) {
        List<Tuple> replyList = jpaQueryFactory
                .select(qComment, qUsers.thumb, qUsers.nick)
                .from(qComment)
                .join(qUsers)
                .on(qComment.userNo.eq(qUsers.userno))
                .where(qComment.pno.eq(pno).and(qComment.parentCno.eq(cno)))
                .orderBy(qComment.cno.asc())
                .fetch();

        return replyList.stream().map(
                tuple -> {
                    Comment comment = tuple.get(0, Comment.class);
                    String thumb = tuple.get(1, String.class);
                    String nick = tuple.get(2, String.class);
                    CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
                    commentDTO.setThumb(thumb);
                    commentDTO.setNick(nick);
                    return commentDTO;
                }).toList();
    }
}
