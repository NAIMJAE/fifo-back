package kr.co.fifoBack.repository.grade.custom;

import java.util.List;

public interface LanguageRepositoryCustom {
    List<String> getDistinctLanguage(int userno);
}
