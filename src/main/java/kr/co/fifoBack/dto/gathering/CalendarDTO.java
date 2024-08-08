package kr.co.fifoBack.dto.gathering;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalendarDTO {
    private int calno;

    private int mooimno;    // 모임 번호
    private String id;
    private String calendarid;
    private String title;
    private LocalDateTime start; // 시작 시간
    private LocalDateTime eventend; // 종료 시간
    private String bgcolor;
    private boolean isallday;
    private boolean isreadonly;
    private String location;
    private String state;
    private String color;

}
