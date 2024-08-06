package kr.co.fifoBack.entity.mooim;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="Chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatno;
    private int mooimno;
    private int userno;
    private String message;
    private LocalDateTime chatdate;
}
