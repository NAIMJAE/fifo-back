package kr.co.fifoBack.dto.grade;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CodeExecutionRequestDTO {

    private int questionNo;
    private int userno;
    private String language;
    private String code;
    private int level;

}
