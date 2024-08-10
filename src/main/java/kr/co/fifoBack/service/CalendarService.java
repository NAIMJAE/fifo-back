package kr.co.fifoBack.service;

import kr.co.fifoBack.dto.gathering.CalendarDTO;
import kr.co.fifoBack.entity.gathering.Calendar;
import kr.co.fifoBack.mapper.CalendarMapper;
import kr.co.fifoBack.repository.mooim.CalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final CalendarMapper calendarMapper;
    private final ModelMapper modelMapper;

    // 캘린더 조회
    public ResponseEntity<?> selectCalendars(int mooimno) {

        List<Calendar> calendars = calendarRepository.findByMooimno(mooimno);
        if(calendars.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }else{
            return ResponseEntity.ok().body(calendars);
        }
    }
    // 캘린더 등록
    public ResponseEntity<?> insertCalendar(CalendarDTO calendarDTO) {
        Calendar calendar = modelMapper.map(calendarDTO, Calendar.class);
        calendarRepository.save(calendar);

        return ResponseEntity.ok().body(calendar);
    }
    // 캘린더 수정
    public ResponseEntity<?> updateCalendar(CalendarDTO calendarDTO) {
        List<Calendar> optCalendar = calendarRepository.findById(calendarDTO.getId());
        if (!optCalendar.isEmpty()) {
            calendarMapper.updateEvent(calendarDTO);
            return ResponseEntity.ok().body(1);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Calendar not found");
        }
    }

    // 캘린더 삭제
    @Transactional
    public ResponseEntity<?> deleteCalendar(String id) {
        List<Calendar> optCalendar = calendarRepository.findById(id);
        if(optCalendar.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }else{
            calendarRepository.deleteById(id);
            return ResponseEntity.ok().body(optCalendar.get(0));
        }
    }
}
