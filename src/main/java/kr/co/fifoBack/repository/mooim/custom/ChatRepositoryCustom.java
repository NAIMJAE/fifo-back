package kr.co.fifoBack.repository.mooim.custom;

import com.querydsl.core.Tuple;

import java.util.List;

public interface ChatRepositoryCustom {
    // 채팅 불러오기
    public List<Tuple> findByMooimno(int mooimno);
}
