package kr.co.fifoBack.service;

import com.querydsl.core.Tuple;
import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.dto.PageResponseDTO;
import kr.co.fifoBack.dto.grade.CodeExecutionRequestDTO;
import kr.co.fifoBack.dto.grade.SolveDTO;
import kr.co.fifoBack.entity.Users;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResponseDTO pageQuestionList = PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(questions)
                .total(questions.size())
                .build();
        return ResponseEntity.ok().body(pageQuestionList);
    }

    /* 문제 리스트 검색 */
    public ResponseEntity<?> searchQuestionsByKeyword(PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable("questionno");

        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();
        log.info(type);
        log.info(keyword);
        Page<Question> searchResultList = null;
        if(type.equals("level")){
            int level = Integer.parseInt(keyword);
            searchResultList = questionRepository.findByLevel(level, pageable);
        }else if(type.equals("title")){
            searchResultList = questionRepository.findByTitleContaining(keyword, pageable);
        }else if(type.equals("number")){
            int qno = Integer.parseInt(keyword);
            searchResultList = questionRepository.findByQuestionno(qno, pageable);
        }else{
            log.info(keyword);
            return ResponseEntity.status(400).body("없음");
        }
        log.info(searchResultList.toString());
        PageResponseDTO pageQuestionList = PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(searchResultList.toList())
                .total(searchResultList.getTotalPages())
                .build();
        return ResponseEntity.ok().body(pageQuestionList);
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
        return solveRepository.findByQuestionnoAndUsernoOrderBySolveddate(questionNo, userNo);
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

    public List<SolveDTO> selectAllSolve(int questionNo){
        List<Tuple> result = solveRepository.getSolvedQuestionByQuestionId(questionNo);
        List<SolveDTO> solvedQuestions = result.stream()
                .map(tuple -> {
                    Solve solve = tuple.get(0, Solve.class);
                    Users user = tuple.get(1, Users.class);
                    SolveDTO solveDTO = modelMapper.map(solve, SolveDTO.class);
                    solveDTO.setNick(user.getNick());
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

        // 문제 풀이에 필요한 내용 설정
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

        // bash 실행할 내용
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

            /**
             * Docker이미지 생성 명령 수행
             * ProcessBuilder를 통해 현재 실행되고 있는 서버에서 CMD명령을 실행
             * 임시로 만들어진 디렉토리에서 Dockerfile등 실행에 필요한 파일을 이용해서 docker build 명령 수행
             * 정상 종료될때까지 ProcessBuilder 유지
             * */
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(String.valueOf(codeDir)));
            processBuilder.command("docker", "build", "-t", uuid, ".");

            Process process = processBuilder.start();

            int buildExitCode = process.waitFor();
            // 결과 출력을 위한 StringBuilder
            StringBuilder output = new StringBuilder();
            // build가 정상 종료 시
            if(buildExitCode == 0){
                /**
                 * Docker run 명령 수행
                 * 생성된 이미지를 실행시켜 입력받은 코드를 실행시킴
                 * */
                ProcessBuilder runProcessBuilder = new ProcessBuilder();
                //?
                processBuilder.directory(new File(String.valueOf(codeDir)));
                // --rm 명령으로 실행 한 컨테이너 삭제
                runProcessBuilder.command("docker", "run", "--rm", uuid);
                Process run = runProcessBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(run.getInputStream()));

                String line;

                // 프로세스의 출력을 읽어오기
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                    log.info(line);
                }

                /**
                 * 프로세스 종료시 실행
                 * docker 이미지와 임시 디렉토리 삭제
                 * */
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
                // 코드 실행 종료 대기
                run.waitFor();
            }
            // 임시 디렉토리 내용 및 디렉토리 삭제
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
