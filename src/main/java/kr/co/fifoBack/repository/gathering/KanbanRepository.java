package kr.co.fifoBack.repository.gathering;

import kr.co.fifoBack.entity.gathering.Kanban;
import kr.co.fifoBack.entity.gathering.MooimMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KanbanRepository extends JpaRepository<Kanban, Integer>  {
}
