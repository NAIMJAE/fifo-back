package kr.co.fifoBack.repository.grade.impl;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.fifoBack.entity.grade.QLanguage;
import kr.co.fifoBack.entity.user.QSkill;
import kr.co.fifoBack.repository.grade.custom.LanguageRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LanguageRepositoryImpl implements LanguageRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QLanguage qLanguage=  QLanguage.language;
    private final QSkill qSkill = QSkill.skill;

    @Override
    public List<String> getDistinctLanguage(int userno) {

        return jpaQueryFactory.select(qLanguage.languagename)
                .from(qLanguage)
                .where(qLanguage.languagename.notIn(
                        JPAExpressions.select(qSkill.languagename)
                                .from(qSkill)
                                .where(qSkill.userno.eq(userno))
                                .distinct()
                )).fetch();

    }
}
