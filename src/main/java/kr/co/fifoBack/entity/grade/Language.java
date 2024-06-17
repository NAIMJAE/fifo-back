package kr.co.fifoBack.entity.grade;

import jakarta.persistence.*;
import lombok.*;


@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="language")
public class Language {
    @Id
    private String languagename;
    private String type1;
    private String type2;

}
