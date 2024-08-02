package kr.co.fifoBack.repository.mooim;

import kr.co.fifoBack.entity.mooim.Chat;
import kr.co.fifoBack.repository.mooim.custom.ChatRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer>, ChatRepositoryCustom {
}
