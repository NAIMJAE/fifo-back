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
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;
    private String content;
    private int userNo;
    private int pno;
    private int parentCno;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
