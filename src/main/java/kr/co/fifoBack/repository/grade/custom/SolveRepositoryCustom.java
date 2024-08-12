package kr.co.fifoBack.repository.grade.custom;

import com.querydsl.core.Tuple;

import java.util.List;

public interface SolveRepositoryCustom {
    List<Tuple> getUserGradeInfo(int userno);
}
