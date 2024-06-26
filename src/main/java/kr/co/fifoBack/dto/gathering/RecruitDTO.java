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
    private String gathno;
    private int userno;
    private String recruitstate;

}
