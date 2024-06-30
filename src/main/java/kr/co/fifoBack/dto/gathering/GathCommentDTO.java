package kr.co.fifoBack.dto.gathering;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GathCommentDTO {

    private int commentno;
    private int gathno;
    private int userno;
    private String content;
    private LocalDateTime rdate;
}
