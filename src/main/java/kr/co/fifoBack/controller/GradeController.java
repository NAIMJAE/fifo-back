package kr.co.fifoBack.controller;

import jakarta.websocket.server.PathParam;
import kr.co.fifoBack.service.GradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/question/list/{language}")
    public ResponseEntity<?> questionList(@PathVariable("language") String language){
        log.info("여기");
        return gradeService.selectAllQuestionsByLanguage(language);
    }

    @GetMapping("question/{questionNo}")
    public ResponseEntity<?> questionView(@PathVariable("questionNo") int questionNo){
        log.info("여기");
        return gradeService.selectQuestionByQuestionNo(questionNo);
    }

}
