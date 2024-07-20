package kr.co.fifoBack.entity.user;

import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.UndeclaredThrowableException;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="userregion")
public class UserRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rno")
    private int urno;
    private int userno;
    private String regionname;
    private int countpeople;
}
