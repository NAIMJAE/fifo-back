package kr.co.fifoBack.repository.post.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.entity.QUser;
import kr.co.fifoBack.entity.post.Post;
import kr.co.fifoBack.entity.post.QPost;
import kr.co.fifoBack.repository.post.custom.PostRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QPost qPost = QPost.post;
    private final QUser qUser = QUser.user;

    // 게시글 조회 + 검색
    public void selectPostByKeyword(PageRequestDTO pageRequestDTO, Pageable pageable) {

        BooleanExpression expression = qPost.cateNo.eq(pageRequestDTO.getCateNo());
        OrderSpecifier<?> orderSpecifier = null;

        // 기본값
        if (pageRequestDTO.getCateNo() > 1) {
            expression = expression.and(qPost.cateNo.eq(pageRequestDTO.getCateNo()));
        }

        // 카테고리
        if (pageRequestDTO.getType().equals("title")) {
            expression = expression.and(qPost.title.contains(pageRequestDTO.getKeyword()));
        } else if (pageRequestDTO.getType().equals("content")) {
            expression = expression.and(qPost.content.contains(pageRequestDTO.getKeyword()));
        } else if (pageRequestDTO.getType().equals("writer")) {
            expression = expression.and(qUser.nick.contains(pageRequestDTO.getKeyword()));
        }

        // 정렬
        if (pageRequestDTO.getSort().equals("new")) {
            orderSpecifier = qPost.createDate.desc();
        }else if (pageRequestDTO.getSort().equals("hit")) {
            orderSpecifier = qPost.hit.desc();
        }else if (pageRequestDTO.getSort().equals("good")) {
            orderSpecifier = qPost.good.desc();
        }else if (pageRequestDTO.getSort().isEmpty()){
            orderSpecifier = qPost.createDate.desc();
        }

        QueryResults<Post> result = jpaQueryFactory
                .selectFrom(qPost)
                .where(expression)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        log.info("result : " + result);

    }

}
