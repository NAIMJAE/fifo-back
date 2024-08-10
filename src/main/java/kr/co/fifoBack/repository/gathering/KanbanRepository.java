package kr.co.fifoBack.repository.gathering;

import kr.co.fifoBack.entity.gathering.Kanban;
import kr.co.fifoBack.entity.gathering.MooimMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KanbanRepository extends JpaRepository<Kanban, Integer>  {
    // 칸반 조회
    public Optional<Kanban> findByMooimno(int mooimno);
}
