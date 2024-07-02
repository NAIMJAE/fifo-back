package kr.co.fifoBack.entity.grade;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="questionexample")
public class QuestionExample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int exampleno;      // 문제 예제 번호
    private int questionno;     // 문제 번호
    private String input;       // 문제 예제 입력 값
    private String answer;      // 문제 예제 답

}
