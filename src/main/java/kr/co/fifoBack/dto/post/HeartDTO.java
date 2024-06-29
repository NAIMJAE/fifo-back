package kr.co.fifoBack.dto.post;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeartDTO {
    private int hNo;
    private int userNo;
    private int pno;
    private LocalDateTime cDate;
}
