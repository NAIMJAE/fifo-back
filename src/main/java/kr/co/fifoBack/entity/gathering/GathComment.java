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
@Table(name = "gathComment")
public class GathComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentno;
    private String gathno;
    private int userno;
    private String content;
    private LocalDateTime rdate;
}
