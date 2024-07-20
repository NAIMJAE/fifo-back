package kr.co.fifoBack.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sno;
    private int userno;
    private String languagename;
    @Builder.Default
    private int level = 1;
}
