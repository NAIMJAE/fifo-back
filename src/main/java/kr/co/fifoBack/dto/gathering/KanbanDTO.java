package kr.co.fifoBack.dto.gathering;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KanbanDTO {

    private int mooinno; // 모임 번호
    private int kanno; // 칸반 번호
    private String kanstatus; // 칸반 상태
    private String content; // 내용

}
