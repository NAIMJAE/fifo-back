package kr.co.fifoBack.dto.post;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {
    private int cateNo;
    private String cateName;
}
