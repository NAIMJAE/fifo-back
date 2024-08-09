package kr.co.fifoBack.mapper;

import kr.co.fifoBack.dto.user.ExperienceDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExperienceMapper {
    // 수정
    public int updateExp(ExperienceDTO experienceDTO);
}
