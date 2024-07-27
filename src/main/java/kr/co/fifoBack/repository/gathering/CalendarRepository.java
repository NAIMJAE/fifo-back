package kr.co.fifoBack.repository.gathering;

import kr.co.fifoBack.entity.gathering.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer>  {
}
