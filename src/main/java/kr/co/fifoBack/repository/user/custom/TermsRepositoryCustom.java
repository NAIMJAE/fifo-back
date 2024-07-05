package kr.co.fifoBack.repository.user.custom;

import kr.co.fifoBack.entity.user.Terms;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsRepositoryCustom {
    public String getTerms();
    public String getPrivacy();
}
