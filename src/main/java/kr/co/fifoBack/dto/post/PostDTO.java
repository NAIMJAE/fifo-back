package kr.co.fifoBack.dto.post;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {
    private int pno;
    private String title;
    private String content;
    private int userNo;
    private int cateNo;
    private int good;
    private int hit;
    private int comNum;
    private int heartNum;
    private LocalDateTime createDate;
    private LocalDateTime modiDate;

    // 추가 필드
    private String tag;
    private List<MultipartFile> files;
    private List<MultipartFile> images;

    private String thumb;
    private String nick;
    private List<String> tagName;

    private List<String> fileName;
    private String cateName;
}
