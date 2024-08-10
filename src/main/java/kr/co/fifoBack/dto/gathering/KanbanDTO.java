package kr.co.fifoBack.dto.gathering;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KanbanDTO {

    private int mooimno; // 모임 번호
    private int kanno; // 칸반 번호
    private String content; // 내용

    // 추가 필드
    private int progress;
}
