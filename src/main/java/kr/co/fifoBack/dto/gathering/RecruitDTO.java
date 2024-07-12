package kr.co.fifoBack.dto.gathering;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruitDTO {

    private int recruitno;
    private int gathno;
    private int userno;
    private String recruitstate;
    private String intro;

    // 추가 필드
    private String nick;
    private String region;
    private String thumb;

}
