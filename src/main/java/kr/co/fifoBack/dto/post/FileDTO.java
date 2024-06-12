package kr.co.fifoBack.dto.post;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDTO {
    private int fno;
    private int pno;
    private String sName;
    private LocalDateTime cDate;
}
