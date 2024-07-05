package kr.co.fifoBack.repository.gathering;

import kr.co.fifoBack.entity.gathering.GathComment;
import kr.co.fifoBack.entity.gathering.Gathering;
import kr.co.fifoBack.repository.gathering.custom.GathCommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GathCommentRepository extends JpaRepository<GathComment, Integer>, GathCommentRepositoryCustom {
}
