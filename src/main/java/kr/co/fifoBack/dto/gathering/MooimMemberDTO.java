package kr.co.fifoBack.dto.gathering;

import jakarta.persistence.*;
import kr.co.fifoBack.dto.user.UsersDTO;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MooimMemberDTO {

    private int memberno;

    private int mooimno;
    private int userno;

    private UsersDTO usersDTO;
}
