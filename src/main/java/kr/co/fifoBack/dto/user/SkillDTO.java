package kr.co.fifoBack.dto.user;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillDTO {
    private int sno;
    private int userno;
    private String languagename;
    private int level;
}
