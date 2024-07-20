package kr.co.fifoBack.dto.user;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDTO {
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
    private String role;
    private int stack;

    // 스킬 목록 저장
    private String[] languagename;
    private Integer[] levels;

    @CreationTimestamp
    private LocalDateTime rdate;
    @CreationTimestamp
    private LocalDateTime leaveDate;
}
