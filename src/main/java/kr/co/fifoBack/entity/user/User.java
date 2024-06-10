package kr.co.fifoBack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="user")
public class User {
    @Id
    private int userNo;
    private String email;
    private String pass;
    private String name;
    private String nick;
    private String hp;
    private String region;
    private int age;
    private String gender;
    private String thumb;
    private String role;

    @CreationTimestamp
    private LocalDateTime rdate;
    @CreationTimestamp
    private LocalDateTime leaveDate;
}
