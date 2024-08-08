package kr.co.fifoBack.dto.user;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceDTO {
    private int exeno;
    private int userno;
    private String job;
    private String skill;
    private String period;

}
