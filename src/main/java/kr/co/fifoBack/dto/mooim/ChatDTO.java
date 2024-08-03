package kr.co.fifoBack.dto.mooim;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    // 추가 필드
    private Map<String, String> msgData;
    private String nick;
    private String thumb;
    private List<MultipartFile> file;
}
