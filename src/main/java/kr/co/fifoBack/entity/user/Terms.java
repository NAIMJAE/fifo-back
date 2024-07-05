package kr.co.fifoBack.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="terms")
public class Terms {
    @Id
    private String terms;
    private String privacy;
    private String sms;

}
