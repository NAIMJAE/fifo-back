package kr.co.fifoBack.entity.gathering;

import jakarta.persistence.*;
import lombok.*;

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
    private String gathcate;    // 모집 유형 (프로젝트, 스터디, 모임)
    private int userno;         // 모임 호스트 (게시글 작성자)
    private String gathtitle;   // 게시글 제목
    private String gathdetail;  // 게시글 내용 (content)
    private String gathmode;    // 모임 방식 (온/오프라인)
    private int gathnowmember;  // 모집된 인원
    private int gathtotalmember;// 모집할 총 인원
    private String gathsupport; // 지원 방법(오픈카톡/ 메일... 자체 지원 제공해서 삭제할 예정?)
    private String gathrecruitfield;    // 모집 분야 (프론트/백엔드/디자인) - 구분자로 무식하게 박아넣기
    private String gathlanguage;        // 모집 언어 + lv (Java lv5, React lv1...)
    private LocalDateTime recruitstart;
    private LocalDateTime recruitend;
    private LocalDateTime projectstart;
    private LocalDateTime projectend;
    private String gathstate;   // 모집상태 (모집중, 모집완료)
}
