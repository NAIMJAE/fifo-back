package kr.co.fifoBack.controller;

import kr.co.fifoBack.service.GradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class GradeController {

    private final GradeService gradeService;

    @GetMapping("/language/list")
    public ResponseEntity<?> languageList(){
        return gradeService.selectAllLanguagesByType2();
    }

}
