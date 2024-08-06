package kr.co.fifoBack.mapper;

import kr.co.fifoBack.dto.gathering.CalendarDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CalendarMapper {
    public void updateEvent(@Param("calendarDTO") CalendarDTO calendarDTO);
}
