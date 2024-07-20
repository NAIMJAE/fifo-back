package kr.co.fifoBack.dto.user;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegionDTO {
    private int urno;
    private String regionname;
    private int userno;
}
