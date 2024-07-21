package kr.co.fifoBack.entity.gathering;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "gathering")
public class Gathering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gathno;
    private int gathcate;    // 모집 유형 (1:프로젝트, 2:스터디, 3:모임)
    private int userno;         // 모임 호스트 (게시글 작성자)
    private String gathtitle;   // 게시글 제목
    private String gathdetail;  // 게시글 내용 (content)
    private String gathmode;    // 모임 방식 (온/오프라인)
    private int gathnowmember;  // 모집된 인원
    private int gathtotalmember;// 모집할 총 인원
    private String gathrecruitfield;    // 모집 분야 (프론트/백엔드/디자인) - 구분자로 무식하게 박아넣기
    private String gathlanguage;        // 모집 언어 + lv (Java lv5, React lv1...)
    private String thumb;
    private LocalDate recruitstart;
    private LocalDate recruitend;
    private String mooimperiod; // 모임 예상 기간
    private String gathstate;  // 모집상태 (1:모집중, 2:모집완료)
    private int hit;
    private int gathcomment;    // 댓글 개수

    private LocalDateTime modiDate;
}
