package kr.co.fifoBack.mapper;

import kr.co.fifoBack.dto.user.UsersDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

@Mapper
public interface UsersMapper {
    /**중복검사*/
    public int duplicateTest(@Param("param")String param);

    /**정보 수정*/
    public int updateProfile(@Param("userno")int userno, @Param("type")String type, @Param("information") String information);
}