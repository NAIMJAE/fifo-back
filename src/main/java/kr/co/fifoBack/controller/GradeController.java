package kr.co.fifoBack.controller;

import kr.co.fifoBack.dto.grade.CodeExecutionRequestDTO;
import kr.co.fifoBack.dto.grade.CodeExecutionResponseDTO;
import kr.co.fifoBack.dto.grade.SolveDTO;
import kr.co.fifoBack.entity.grade.Solve;
import kr.co.fifoBack.service.GradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class GradeController {

    private final GradeService gradeService;

    @GetMapping("/grade/{userNo}")
    public Map<String, ?> userGradeInfo(@PathVariable("userNo") int userNo){
        Map<String, List<?>> userGradeInfo = new HashMap<>();
        userGradeInfo.put("solvedQuestions", gradeService.selectSolvedQuestions(userNo));
        userGradeInfo.put("userSkills", gradeService.selectUserSkills(userNo));
        return userGradeInfo;
    }

    @GetMapping("/language/list")
    public ResponseEntity<?> languageList(){
        return gradeService.selectAllLanguagesByType2();
    }

    @GetMapping("/question/list/{language}")
    public ResponseEntity<?> questionList(@PathVariable("language") String language){
        log.info("여기");
        return gradeService.selectAllQuestionsByLanguage(language);
    }

    @GetMapping("/question/{questionNo}")
    public ResponseEntity<?> questionView(@PathVariable("questionNo") int questionNo){
        log.info("여기");
        return gradeService.selectQuestionByQuestionNo(questionNo);
    }

    @GetMapping("/solve/{questionNo}/{userNo}")
    public List<Solve> solveList(@PathVariable("questionNo") int questionNo, @PathVariable("userNo") int userNo){
        log.info("여기");
        return gradeService.selectSolve(questionNo, userNo);
    }

    @GetMapping("/solve/other/{questionNo}")
    public List<SolveDTO> otherSolveList(@PathVariable("questionNo") int questionNo){
        return gradeService.selectAllSolve(questionNo);
    }

    @PostMapping("/question/execute")
    public CodeExecutionResponseDTO executeCode(@RequestBody CodeExecutionRequestDTO request) {
        log.info(request+"이거 한번만");
        String output = gradeService.examineCode(request);
        log.info(output);
        return new CodeExecutionResponseDTO(output);
    }
}
