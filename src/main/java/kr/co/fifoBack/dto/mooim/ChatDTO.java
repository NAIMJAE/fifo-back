package kr.co.fifoBack.dto.mooim;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatDTO {
    private int chatno;
    private int mooimno;
    private int userno;
    private String message;
    private LocalDateTime chatdate;
}
