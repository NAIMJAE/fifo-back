package kr.co.fifoBack.service;

import com.querydsl.core.Tuple;
import kr.co.fifoBack.dto.grade.CodeExecutionRequestDTO;
import kr.co.fifoBack.dto.grade.SolveDTO;
import kr.co.fifoBack.entity.grade.Language;
import kr.co.fifoBack.entity.grade.Question;
import kr.co.fifoBack.entity.grade.QuestionIOData;
import kr.co.fifoBack.entity.grade.Solve;
import kr.co.fifoBack.entity.user.Skill;
import kr.co.fifoBack.repository.grade.LanguageRepository;
import kr.co.fifoBack.repository.grade.QuestionIODataRepository;
import kr.co.fifoBack.repository.grade.QuestionRepository;
import kr.co.fifoBack.repository.grade.SolveRepository;
import kr.co.fifoBack.repository.user.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeService {


    private final ModelMapper modelMapper;

    private final LanguageRepository languageRepository;
    private final QuestionRepository questionRepository;
    private final QuestionIODataRepository questionIODataRepository;
    private final SolveRepository solveRepository;
    private final SkillRepository skillRepository;

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

    public ResponseEntity<?> selectQuestionByQuestionNo(int questionNo){

        Optional questionOptional = questionRepository.findById(questionNo);

        if (questionOptional.isPresent()){
            Question question = modelMapper.map(questionOptional.get(),Question.class);
            return ResponseEntity.ok().body(question);
        }

        return ResponseEntity.status(400).body("없음");
    }

    public Solve insertSolve(SolveDTO solveDTO){
        Solve solve = modelMapper.map(solveDTO, Solve.class);
        return solveRepository.save(solve);
    }

    public List<Solve> selectSolve(int questionNo, int userNo){
        return solveRepository.findByQuestionnoAndUsernoOrderBySolveid(questionNo, userNo);
    }

    public List<Skill> selectUserSkills(int userNo){
        return skillRepository.findTop5ByUsernoOrderByExperienceDesc(userNo);
    }

    public List<SolveDTO> selectSolvedQuestions(int userNo){
        List<Tuple> result = solveRepository.getUserGradeInfo(userNo);
        log.info("이거 봐바"+result.toString());
        List<SolveDTO> solvedQuestions = result.stream()
                .map(tuple -> {
                    Solve solve = tuple.get(0, Solve.class);
                    Question question = tuple.get(1, Question.class);
                    SolveDTO solveDTO = modelMapper.map(solve, SolveDTO.class);
                    solveDTO.setLanguagename(question.getLanguagename());
                    solveDTO.setTitle(question.getTitle());
                    solveDTO.setLevel(question.getLevel());
                    return solveDTO;
                }).toList();
        log.info("이번엔 이거"+solvedQuestions);
        return solvedQuestions;
    }

    public void updateExperience(CodeExecutionRequestDTO requestDTO){
        StringBuilder language = new StringBuilder();
        language.append(requestDTO.getLanguage().substring(0,1).toUpperCase()).append(requestDTO.getLanguage().substring(1));
        log.info(language.toString());
        Optional<Skill> skillOpt = skillRepository.findByUsernoAndLanguagename(requestDTO.getUserno(), language.toString());
        if(skillOpt.isEmpty()){
            //저장
            Skill insertSkill = Skill.builder()
                    .userno(requestDTO.getUserno())
                    .languagename(language.toString())
                    .experience(requestDTO.getLevel() * 100)
                    .build();
            skillRepository.save(insertSkill);
        }else{
            Skill skill = modelMapper.map(skillOpt, Skill.class);
            Skill updateSkill = Skill.builder()
                    .sno(skill.getSno())
                    .userno(requestDTO.getUserno())
                    .languagename(language.toString())
                    .experience(skill.getExperience() + (requestDTO.getLevel() * 100))
                    .build();
            skillRepository.save(updateSkill);
        }
    }

    public String examineCode(CodeExecutionRequestDTO request){
        List<QuestionIOData> IOData = selectQuestionIOData(request.getQuestionNo());

        if (!IOData.isEmpty()){
            for(QuestionIOData data : IOData){
                if (!data.getOutput().equals(executeCode(request, data.getInput()))){
                    return "틀렸습니다.";
                }
            }
            return "정답입니다.";
        }
        return "문제 오류";
    }

    public String executeCode(CodeExecutionRequestDTO request, String input) {

        String language = request.getLanguage();
        String code = request.getCode();

        /* docker에 필요한 파일(임시) */
        String dockerFileContent =
                """
                        FROM openjdk:11
                        WORKDIR /app
                        COPY . /app
                        RUN javac Main.java
                        COPY run.sh /app/run.sh
                        RUN chmod +x /app/run.sh
                        CMD ["/app/run.sh"]""";

        String runShell = """
                #!/bin/sh
                cat input.txt | java Main
                """;

        try {
            // 코드 저장 디렉토리 생성
            String uuid = UUID.randomUUID().toString();

            Path codeDir = Paths.get("code", uuid);
            Files.createDirectories(codeDir);

            // 코드 파일 생성
            Path codeFile = codeDir.resolve(getFileName(language));
            Files.write(codeFile, code.getBytes());

            // Docker 이미지 파일 및 생성 스크립트 생성
            Path dockerFile = codeDir.resolve("Dockerfile");
            Files.write(dockerFile, dockerFileContent.getBytes());

            Path inputText = codeDir.resolve("input.txt");
            Files.write(inputText, input.getBytes());

            Path runShellFile = codeDir.resolve("run.sh");
            Files.write(runShellFile, runShell.getBytes());

            // 명령
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(String.valueOf(codeDir)));
            processBuilder.command("docker", "build", "-t", uuid, ".");

            Process process = processBuilder.start();

            // 프로세스 완료까지 대기
            int buildExitCode = process.waitFor();
            StringBuilder output = new StringBuilder();
            if(buildExitCode == 0){
                // 이전 프로세스(build가 정상 종료 시)
                ProcessBuilder runProcessBuilder = new ProcessBuilder();
                processBuilder.directory(new File(String.valueOf(codeDir)));
                runProcessBuilder.command("docker", "run", "--rm", uuid);
                Process run = runProcessBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(run.getInputStream()));

                String line;

                // 프로세스의 출력을 읽어오기
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                    log.info(line);
                }

                // 프로세스 종료 대기
                CompletableFuture<Process> onExitFuture = run.onExit();
                onExitFuture.thenAccept(p -> {
                    try {
                        int runExitCode = p.exitValue();
                        log.info("Process exited with code: " + runExitCode);

                        // 도커 이미지 삭제
                        ProcessBuilder rmiProcessBuilder = new ProcessBuilder();
                        rmiProcessBuilder.command("docker", "rmi", uuid);
                        Process rmiProcess = rmiProcessBuilder.start();

                        BufferedReader rmiReader = new BufferedReader(new InputStreamReader(rmiProcess.getInputStream()));
                        String rmiLine;
                        while ((rmiLine = rmiReader.readLine()) != null) {
                            log.info(rmiLine);
                        }
                        rmiProcess.waitFor();
                    } catch (IOException | InterruptedException e) {
                        log.info("Error while deleting Docker image: " + e.getMessage());
                    }
                });

                run.waitFor();

            }

            File directory = new File(codeDir.toString());
            deleteFiles(directory);

            // 실행 결과 출력
            return output.toString();

        } catch (Exception e) {
            log.info(e.getMessage());
            return e.getMessage();
        }
    }

    public List<QuestionIOData> selectQuestionIOData(int questionNo){
        return questionIODataRepository.findByQuestionno(questionNo);
    }

    private String getFileName(String language) {
        switch (language) {
            case "java":
                return "Main.java";
            case "python":
                return "script.py";
            case "javascript":
                return "script.js";
            case "c":
                return "program.c";
            case "cpp":
                return "program.cpp";
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }

    private String getImageName(String language) {
        switch (language) {
            case "java":
                return "openjdk:11";
            case "python":
                return "python:3.8";
            case "javascript":
                return "node:14";
            case "c":
            case "cpp":
                return "gcc:latest";
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }

    private boolean deleteFiles(File codeFile){
        File[] allFiles = codeFile.listFiles();
        if(allFiles != null){
            for(File file : allFiles){
                deleteFiles(file);
            }
        }
        log.info("delete file log : " + codeFile.getPath());
        return codeFile.delete();
    }

}
