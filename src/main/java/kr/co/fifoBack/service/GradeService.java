package kr.co.fifoBack.service;

import kr.co.fifoBack.entity.grade.Language;
import kr.co.fifoBack.entity.grade.Question;
import kr.co.fifoBack.repository.grade.LanguageRepository;
import kr.co.fifoBack.repository.grade.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeService {


    private final ModelMapper modelMapper;

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

    public ResponseEntity<?> selectQuestionByQuestionNo(int questionNo){

        Optional questionOptional = questionRepository.findById(questionNo);

        if (questionOptional.isPresent()){
            Question question = modelMapper.map(questionOptional.get(),Question.class);
            return ResponseEntity.ok().body(question);
        }

        return ResponseEntity.status(100).body("없음");
    }

    public String executeCode(String language, String code) {

        String containerId = null;
        /* docker에 필요한 파일(임시) */
        String dockerFileContent =
                "FROM openjdk:11\n" +
                "WORKDIR /app\n" +
                "COPY . /app\n" +
                "RUN javac Main.java\n" +
                "COPY run.sh /app/run.sh\n" +
                "RUN chmod +x /app/run.sh\n" +
                "CMD [\"/app/run.sh\"]";

        String input = "3 4\n" +
                "ohhenrie\n" +
                "charlie\n" +
                "baesangwook\n" +
                "obama\n" +
                "baesangwook\n" +
                "ohhenrie\n" +
                "clinton";

        String runShell = "#!/bin/sh\n" +
                "cat input.txt | java Main\n";

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
            int exitCode = process.waitFor();
            StringBuilder output = new StringBuilder();
            if(exitCode == 0){
                // 이전 프로세스(build가 정상 종료 시)
                ProcessBuilder runProcessBuilder = new ProcessBuilder();
                processBuilder.directory(new File(String.valueOf(codeDir)));
                runProcessBuilder.command("docker", "run", "--rm", uuid);
                Process run = runProcessBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(run.getInputStream()));

                String line;

                // 프로세스의 출력을 읽어오기
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                    log.info(line);
                }
                run.waitFor();
            }
            // 실행 결과 출력
            return output.toString();

        } catch (Exception e) {
            log.info(e.getMessage());
            return e.getMessage();
        }
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

}
