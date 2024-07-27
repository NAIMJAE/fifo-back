package kr.co.fifoBack.dto.user;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDTO {
    private int jno;
    private String jobname;
}
