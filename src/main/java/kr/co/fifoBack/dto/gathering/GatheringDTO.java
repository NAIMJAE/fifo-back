package kr.co.fifoBack.dto.gathering;

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
public class GatheringDTO {

    private int gathno;
    private String gathcate;
    private int userno;
    private String gathtitle;
    private String gathdetail;
    private String gathmode;
    private int gathnowmember;
    private int gathtotalmember;
    private String gathsupport;
    private String gathrecruitfield;
    private String gathlanguage;
    private LocalDateTime recruitstart;
    private LocalDateTime recruitend;
    private LocalDateTime projectstart;
    private LocalDateTime projectend;
    private String gathstate;

    private List<MultipartFile> images;
}
