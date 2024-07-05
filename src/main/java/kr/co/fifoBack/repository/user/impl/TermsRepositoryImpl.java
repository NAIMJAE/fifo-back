package kr.co.fifoBack.repository.user.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.fifoBack.entity.user.QTerms;
import kr.co.fifoBack.entity.user.Terms;
import kr.co.fifoBack.repository.user.custom.TermsRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class TermsRepositoryImpl implements TermsRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QTerms qTerms = QTerms.terms1;
    @Override
    public String getTerms() {
        return jpaQueryFactory.select(qTerms.terms).from(qTerms).fetchOne();
    }

    @Override
    public String getPrivacy() {
        return jpaQueryFactory.select(qTerms.privacy).from(qTerms).fetchOne();
    }
}
