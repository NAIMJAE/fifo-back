package kr.co.fifoBack.dto.gathering;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MooimDTO {

    private int mooimno;

    private int gathno;
    private int userno;
    private LocalDate mooimstart;
    private LocalDate mooimend;
    @Builder.Default
    private int mooimstate = 1; // 1:진행중 2:완료
    private String mooimtitle;
    private String mooimintro;
    private int mooimcate; // 1:프로젝트 2:스터디 3:모임
    private String thumb;

    // 참가 멤버 정보
    private List<RecruitDTO> recruitMember;
    private List<MooimMemberDTO> memberList;

    // 저장용 변수
    private MultipartFile thumbnail;
}
