package kr.co.fifoBack.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MooimMapper {
    public void updateMooimIntro (@Param("mooimno") int mooimno, @Param("mooimintro") String mooimintro );
}
