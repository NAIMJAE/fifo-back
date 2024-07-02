package kr.co.fifoBack.entity.grade;

import jakarta.persistence.*;
import lombok.*;


@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int questionno;         // 문제 번호

    private String title;   // 문제 이름

    private int userno;             // 등록자 번호

    private String explanation;     // 문제 설명

    private int level;              // 문제 난이도

    private boolean compiler;       // 컴파일러 사용 여부

    private String languagename;    // 언어

}
