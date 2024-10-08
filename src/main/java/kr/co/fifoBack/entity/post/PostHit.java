package kr.co.fifoBack.entity.post;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posthit")
public class PostHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hno;
    private Integer pno;
    private Integer gathno;
    private String address;
}
