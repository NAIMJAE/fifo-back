package kr.co.fifoBack.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kr.co.fifoBack.config.GsonLocalDateTimeAdapter;
import kr.co.fifoBack.dto.grade.CodeExecutionRequestDTO;
import kr.co.fifoBack.dto.grade.SolveDTO;
import kr.co.fifoBack.entity.grade.QuestionIOData;
import kr.co.fifoBack.entity.grade.Solve;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    HashMap<String, WebSocketSession> sessionMap = new HashMap<>();
    private final GradeService gradeService;


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        //메시지 발송
        String msg = message.getPayload();
        log.info(msg);

        /**
         * 소켓으로 받은 내용을 json형식으로 변환
         * ObjectMapper로 역직렬화 및 DTO 클래스로 매핑
         */
        ObjectMapper mapper = new ObjectMapper();
        CodeExecutionRequestDTO requestDTO = null;
        try {
            requestDTO = mapper.readValue(msg, CodeExecutionRequestDTO.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info(requestDTO.toString());

        /**
         * 풀이 임시저장
         * 문제를 푼 후 풀이화면으로 넘어갔을때 해당 풀이에 대한 내용을 출력하기 위해 임시로 저장하여 id값 등의 내용을 얻음
         * */
        SolveDTO solveDTO = SolveDTO.builder()
                .userno(requestDTO.getUserno())
                .questionno(requestDTO.getQuestionNo())
                .code(requestDTO.getCode())
                .build();
        Solve savedSolve = gradeService.insertSolve(solveDTO);

        /**
         * 임시 저장된 풀이 내용을 json으로 변환
         * json변환시 LocalDateTime, LocalDate 는 gson이 제대로 알아먹질 못하여 설정을 해줘야함(config/GsonLocalDateTImeAdapter)
         * 소켓으로 객체를 보내는 방법을 생각 안하려고 한것같음
         * */
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
        String solveInfo = gson.toJson(savedSolve);

        /**
         * 최종 결과를 미리 100(정답)으로 설정하여 이상이 있거나 틀렸을때 값을 변경하도록 구현
         * result 값
         * 0~99 풀이중
         * 100 정답
         *
         * 200 오답
         * 201~299 오류 유형 분류 예정
         *
         * 300 문제오류
         * 301~399 문제 오류 유형 분류 예정
         *
         * 400 이상 미정
         * */
        String result = "100";

        for(String key : sessionMap.keySet()) {
            log.info(key);
            WebSocketSession wss = sessionMap.get(key);

            try {
                // 임시저장 내용 전송
                wss.sendMessage(new TextMessage(solveInfo));

                /**
                 * 문제별 입출력 데이터 테이블을 조회해서 없을경우 문제이상(300)으로 전송
                 * */
                List<QuestionIOData> IOData = gradeService.selectQuestionIOData(requestDTO.getQuestionNo());

                if (!IOData.isEmpty()){
                    float count = 1;
                    float size = IOData.size();
                    for(QuestionIOData data : IOData){
                        /**
                         * 문제 풀이 시작부분 executeCode
                         * 코드 실행결과가 문제 입출력 데이터와 일치하지 않으면 200으로 더 이상 실행 종료
                         * */
                        if (!data.getOutput().equals(gradeService.executeCode(requestDTO, data.getInput()))){
                            wss.sendMessage(new TextMessage("200"));
                            result = "200";
                            break;
                        }
                        wss.sendMessage(new TextMessage(count / size + ""));
                        count+=1;
                    }
                }else{
                    // 문제 입출력 데이터가 존재하지 않을때
                    wss.sendMessage(new TextMessage("300"));
                    result = "300";
                }

            }catch(Exception e) {
                log.error(e.getMessage());
            }

            /**
             * 문제 풀이 후 풀이 테이블에 문제 푼 결과값 업데이트
             * */
            solveDTO.setSolveid(savedSolve.getSolveid());
            solveDTO.setSolveddate(savedSolve.getSolveddate());
            solveDTO.setSolved(result);

            // 풀이가 정답일때
            if(Integer.parseInt(result) == 100){

                /**
                 * 같은 문제 여러번 풀었을때는 경험치를 중복으로 주면 안됨
                 * 문제를 푼적이 있는가 조회 후 정답 기록이 없을때 만 경험치 부여
                 * */
                // 경험치 중복 검사
                boolean isSolvedBefore = false;
                List<Solve> checkSolved = gradeService.selectSolve(requestDTO.getQuestionNo(), requestDTO.getUserno());
                log.info(checkSolved.toString());
                if(!checkSolved.isEmpty()){
                    for(Solve check : checkSolved){
                        if(check.getSolved() != null && Integer.parseInt(check.getSolved()) == 100){
                            isSolvedBefore = true;
                            break;
                        }
                    }
                }
                if(!isSolvedBefore){
                    gradeService.updateExperience(requestDTO);
                }
            }
            gradeService.insertSolve(solveDTO);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //소켓 연결
        log.info("here//");
        super.afterConnectionEstablished(session);
        sessionMap.put(session.getId(), session);
        log.info(session.getId());
        log.info("here2//");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //소켓 종료
        sessionMap.remove(session.getId());
        super.afterConnectionClosed(session, status);
    }

}

