package kr.co.fifoBack.repository.user;

import kr.co.fifoBack.entity.Users;
import kr.co.fifoBack.repository.user.custom.TermsRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);

}
