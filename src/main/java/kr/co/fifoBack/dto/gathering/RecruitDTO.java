package kr.co.fifoBack.dto.gathering;

import jakarta.persistence.*;
import kr.co.fifoBack.dto.user.SkillDTO;
import kr.co.fifoBack.dto.user.UserRegionDTO;
import kr.co.fifoBack.entity.user.UserRegion;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruitDTO {

    private int recruitno;
    private int gathno;
    private int userno;
    private String recruitstate;
    private String intro;

    // 추가 필드
    private String nick;
    private String thumb;
    private int stack;
    private List<UserRegionDTO> userRegions;
    private List<SkillDTO> skill;

}
