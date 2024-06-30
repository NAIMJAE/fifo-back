package kr.co.fifoBack.entity.post;

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
@Table(name = "heart")
public class Heart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hNo;
    private int userNo;
    private int pno;
    @CreationTimestamp
    private LocalDateTime cDate;
}
