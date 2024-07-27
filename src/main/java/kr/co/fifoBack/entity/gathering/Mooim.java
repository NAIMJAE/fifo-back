package kr.co.fifoBack.entity.gathering;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "mooim")
public class Mooim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mooimno;

    private int gathno;
    private int userno;

    @CreationTimestamp
    private LocalDate mooimstart;
    private LocalDate mooimend;
    private int mooimstate; // 1:진행중 2:완료
    private String mooimtitle;
    private int mooimcate; // 1:프로젝트 2:스터디 3:모임
    private String thumb;
}
