package kr.co.fifoBack.dto.gathering;

import kr.co.fifoBack.entity.gathering.Recruit;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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
    private int gathcate;
    private int userno;
    private String gathtitle;
    private String gathdetail;
    private String gathmode;
    private String thumb;
    private int gathnowmember;
    private int gathtotalmember;
    private String gathrecruitfield;
    private String gathlanguage;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate recruitstart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate recruitend;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectstart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectend;
    private String gathstate;

    private int hit;
    private int gathcomment;    // 댓글 개수

    private LocalDateTime modiDate;

    // 저장용 변수
    private MultipartFile thumbnail;
    private List<MultipartFile> images;

    // JOIN 변수
    private String usernick;
    private String userthumb;

    // 추가 필드
    private List<RecruitDTO> recruitList; // 모임 신청 현황
}
