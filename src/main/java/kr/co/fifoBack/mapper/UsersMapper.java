package kr.co.fifoBack.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UsersMapper {
    /**중복검사*/
    public int duplicateTest(@Param("param")String param);
}
