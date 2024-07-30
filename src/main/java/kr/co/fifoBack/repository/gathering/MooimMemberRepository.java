package kr.co.fifoBack.repository.gathering;

import kr.co.fifoBack.entity.gathering.MooimMember;
import kr.co.fifoBack.repository.gathering.custom.MooimMemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MooimMemberRepository extends JpaRepository<MooimMember, Integer>, MooimMemberRepositoryCustom {
}
