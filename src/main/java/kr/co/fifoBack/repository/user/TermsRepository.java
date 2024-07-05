package kr.co.fifoBack.repository.user;

import kr.co.fifoBack.entity.user.Terms;
import kr.co.fifoBack.repository.user.custom.TermsRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsRepository extends JpaRepository<Terms, String >, TermsRepositoryCustom {
}
