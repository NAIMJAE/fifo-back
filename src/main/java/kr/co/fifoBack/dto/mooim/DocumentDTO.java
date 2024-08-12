package kr.co.fifoBack.dto.mooim;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentDTO {
    private int docno;
    private String title;
    private String content;
    private int mooimno;
}
