package kr.co.fifoBack.service;

import kr.co.fifoBack.entity.gathering.Calendar;
import kr.co.fifoBack.repository.gathering.CalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;

    private final ModelMapper modelMapper;

    // 캘린더 조회
    public ResponseEntity<?> selectCalendars(int mooimno) {

        List<Calendar> calendars = calendarRepository.findByMooinno(mooimno);
        if(calendars.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }else{
            return ResponseEntity.ok().body(calendars);
        }
    }
}
