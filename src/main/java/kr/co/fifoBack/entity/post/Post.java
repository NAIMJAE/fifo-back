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
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pno;
    private String title;
    private String content;
    private int userNo;
    private String nick;
    @Column(name = "cateNo")
    private int cateNo;
    private int good;
    private int hit;
    @CreationTimestamp
    private LocalDateTime createDate;
    private LocalDateTime modiDate;
}
