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
@Table(name = "recruit")
public class Recruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recruitno;
    private String gathno;         // 모집 게시글
    private int userno;            // 모임 지원자
    private String recruitstate;   // 지원 상태 (승인, 대기, 거절)

}
