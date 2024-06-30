package kr.co.fifoBack.service;

import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.entity.grade.Language;
import kr.co.fifoBack.entity.grade.Question;
import kr.co.fifoBack.repository.grade.LanguageRepository;
import kr.co.fifoBack.repository.grade.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeService {

    private final LanguageRepository languageRepository;
    private final QuestionRepository questionRepository;

    /* 언어 리스트 출력 */
    public ResponseEntity<?> selectAllLanguagesByType2(){

        List<String> type2 = new ArrayList<>(Arrays.asList("Language","Framework", "Database"));
        HashMap<String, List<Language>> result = new HashMap<>();
        for(String type : type2){
            List<Language> temp = languageRepository.findByType2(type);
            result.put(type, temp);
        }

        return ResponseEntity.ok().body(result);

    }
    /* 문제 리스트 출력 */
    public ResponseEntity<?> selectAllQuestionsByLanguage(String language){

        List<Question> questions = questionRepository.findAllByLanguagename(language);

        return ResponseEntity.ok().body(questions);
    }

}
