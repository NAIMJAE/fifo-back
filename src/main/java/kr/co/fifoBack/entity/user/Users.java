package kr.co.fifoBack.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userno")
    private int userno;
    private String email;
    private String pass;
    private String name;
    private String nick;
    private String hp;
    private String region;
    private Date birth;
    private String gender;
    private String thumb;
    @Builder.Default
    private String role = "USER";

    @CreationTimestamp
    private LocalDateTime rdate;

    private LocalDateTime leaveDate;
}
