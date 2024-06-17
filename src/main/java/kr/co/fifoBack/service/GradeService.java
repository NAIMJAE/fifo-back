package kr.co.fifoBack.service;

import kr.co.fifoBack.entity.grade.Language;
import kr.co.fifoBack.repository.grade.LanguageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public ResponseEntity<?> selectAllLanguagesByType2(){

        List<String> type2 = new ArrayList<>(Arrays.asList("language","framework", "database"));
        HashMap<String, List<Language>> result = new HashMap<>();
        for(String type : type2){
            List<Language> temp = languageRepository.findByType2(type);
            result.put(type, temp);
        }

        log.info("확인"+result);

        return ResponseEntity.ok().body(result);

    }

}
