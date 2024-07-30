package kr.co.fifoBack.entity.gathering;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "mooimmember")
public class MooimMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberno;

    private int mooimno;
    private int userno;
}
