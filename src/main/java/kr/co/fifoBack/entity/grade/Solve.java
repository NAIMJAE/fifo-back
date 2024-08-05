package kr.co.fifoBack.entity.grade;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="solve")
public class Solve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int solveid;
    private int userno;
    private int questionno;
    private String solved;
    private String code;

    @CreationTimestamp
    private LocalDateTime solveddate;

}
