package kr.co.fifoBack.dto.gathering;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MooimDTO {

    private int mooimno;

    private int gathno;
    private int userno;
    private LocalDate mooimstart;
    private LocalDate mooimend;
    private int mooimstate;
    private String mooimtitle;
    private int mooimcate; // 1:프로젝트 2:스터디 3:모임
    private String thumb;
}
