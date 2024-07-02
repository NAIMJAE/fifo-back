package kr.co.fifoBack.repository.post.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.entity.QUsers;
import kr.co.fifoBack.entity.post.QCategory;
import kr.co.fifoBack.entity.post.QPost;
import kr.co.fifoBack.entity.post.QPostTag;
import kr.co.fifoBack.entity.post.QTags;
import kr.co.fifoBack.repository.post.custom.PostRepositoryCustom;
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
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QPost qPost = QPost.post;
    private final QUsers qUser = QUsers.users;
    private final QPostTag qPostTag = QPostTag.postTag;
    private final QTags qTags = QTags.tags;
    private final QCategory qCategory = QCategory.category;

    // 게시글 조회 + 검색
    public Page<Tuple> selectPostByKeyword(PageRequestDTO pageRequestDTO, Pageable pageable) {

        log.info("pageRequestDTO : " + pageRequestDTO);

        BooleanExpression expression = null;
        OrderSpecifier<?> orderSpecifier = null;

        // 카테고리
        if (pageRequestDTO.getCateNo() > 1) {
            expression = (qPost.cateNo.eq(pageRequestDTO.getCateNo()));
        } else {
            expression = (qPost.cateNo.eq(1));
        }

        // 검색
        if (pageRequestDTO.getType() != null) {
            if (pageRequestDTO.getType().equals("title")) {
                expression = expression.and(qPost.title.contains(pageRequestDTO.getKeyword()));
            } else if (pageRequestDTO.getType().equals("content")) {
                expression = expression.and(qPost.content.contains(pageRequestDTO.getKeyword()));
            } else if (pageRequestDTO.getType().equals("writer")) {
                expression = expression.and(qUser.nick.contains(pageRequestDTO.getKeyword()));
            }
        }

        // 정렬
        if (pageRequestDTO.getSort() == null){
            orderSpecifier = qPost.createDate.desc();
        }else if (pageRequestDTO.getSort().equals("new")) {
            orderSpecifier = qPost.createDate.desc();
        }else if (pageRequestDTO.getSort().equals("hit")) {
            orderSpecifier = qPost.hit.desc();
        }else if (pageRequestDTO.getSort().equals("good")) {
            orderSpecifier = qPost.good.desc();
        }

        // 태그 검색 구현 해야함
        
        QueryResults<Tuple> result = jpaQueryFactory
                .select(qPost, qUser.thumb, qUser.nick)
                .from(qPost)
                .join(qUser)
                .on(qPost.userNo.eq(qUser.userno))
                .where(expression)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        log.info("result : " + result.toString());

        List<Tuple> postList = result.getResults();
        int total = (int) result.getTotal();

        return new PageImpl<>(postList, pageable, total);
    }

    // 게시글 태그 조회
    public List<String> selectTagForPno(int pno) {
        List<String> tagName = jpaQueryFactory
                .select(qTags.tag)
                .from(qPostTag)
                .join(qTags)
                .on(qPostTag.tno.eq(qTags.tno))
                .where(qPostTag.pno.eq(pno))
                .fetch();
        return tagName;
    }

    // 게시글 보기
    public Tuple selectPost(int pno) {
        return jpaQueryFactory
                .select(qPost, qUser.thumb, qUser.nick, qCategory.cateName)
                .from(qPost)
                .join(qUser)
                .on(qPost.userNo.eq(qUser.userno))
                .join(qCategory)
                .on(qPost.cateNo.eq(qCategory.cateNo))
                .where(qPost.pno.eq(pno))
                .fetchOne();
    }
}
