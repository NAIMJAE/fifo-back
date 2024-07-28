package kr.co.fifoBack.repository.gathering.custom;

import com.querydsl.core.Tuple;
import kr.co.fifoBack.entity.gathering.MooimMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MooimMemberRepositoryCustom {

    // 모임 참가자 정보
    public List<Tuple> selectMooimMemberAndSkills(int mooimno);
}
