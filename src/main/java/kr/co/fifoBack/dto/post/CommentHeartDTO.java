package kr.co.fifoBack.dto.post;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentHeartDTO {
    private int cheartno;
    private int userNo;
    private int cno;
    private int pno;
}
