package kr.co.fifoBack.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GatheringMapper {

    public void updateGatheringCommentCount (@Param("gathno") int gathno);
    public void updateGatheringGathnow (@Param("gathno") int gathno);
}
