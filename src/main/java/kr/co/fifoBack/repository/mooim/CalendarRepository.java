package kr.co.fifoBack.repository.mooim;

import kr.co.fifoBack.entity.gathering.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer>  {

    public List<Calendar> findByMooimno(int mooimno);

    public List<Calendar> findById(String id);
    public void deleteById(String id);
}
