package kr.co.fifoBack.entity.gathering;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "gathcomment")
public class GathComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentno;
    private int gathno;
    private int userno;
    private String content;
    @CreationTimestamp
    private LocalDateTime rdate;

    private LocalDateTime updateDate;
}
