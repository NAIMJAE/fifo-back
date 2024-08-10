package kr.co.fifoBack.repository.mooim;

import kr.co.fifoBack.entity.gathering.MooimMember;
import kr.co.fifoBack.repository.mooim.custom.MooimMemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MooimMemberRepository extends JpaRepository<MooimMember, Integer>, MooimMemberRepositoryCustom {
}
