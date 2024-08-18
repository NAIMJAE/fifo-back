package kr.co.fifoBack.dto.grade;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SolveDTO {

    private int solveid;
    private int userno;
    private int questionno;
    private String solved;
    private String code;

    private LocalDateTime solveddate;

    //add

    private LocalDateTime solvedDate;
    private String languagename;
    private String title;
    private int level;
    private String nick;


}
