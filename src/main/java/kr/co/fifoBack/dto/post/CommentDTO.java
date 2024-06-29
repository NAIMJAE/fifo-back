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
public class CommentDTO {
    private int cno;
    private String content;
    private int userNo;
    private String userName;
    private int pno;
    private int parentCno;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
