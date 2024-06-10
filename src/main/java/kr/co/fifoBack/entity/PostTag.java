package kr.co.fifoBack.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posttag")
public class PostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ptNo;
    private int pno;
    private int tno;
}
