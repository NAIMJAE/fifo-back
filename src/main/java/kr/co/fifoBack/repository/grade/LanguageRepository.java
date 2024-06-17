package kr.co.fifoBack.repository.grade;

import kr.co.fifoBack.entity.grade.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, String> {

    public List<Language> findByType2(String type2);

}
