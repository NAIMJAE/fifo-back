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

        ObjectMapper mapper = new ObjectMapper();
        CodeExecutionRequestDTO requestDTO = null;
        try {
            requestDTO = mapper.readValue(msg, CodeExecutionRequestDTO.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info(requestDTO.toString());

        SolveDTO solveDTO = SolveDTO.builder()
                .userno(requestDTO.getUserno())
                .questionno(requestDTO.getQuestionNo())
                .code(requestDTO.getCode())
                .build();

        Solve savedSolve = gradeService.insertSolve(solveDTO);
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
        String solveInfo = gson.toJson(savedSolve);

        String result = "100";

        for(String key : sessionMap.keySet()) {
            log.info(key);
            WebSocketSession wss = sessionMap.get(key);

            try {

                wss.sendMessage(new TextMessage(solveInfo));

                List<QuestionIOData> IOData = gradeService.selectQuestionIOData(requestDTO.getQuestionNo());

                if (!IOData.isEmpty()){
                    float count = 1;
                    float size = IOData.size();
                    for(QuestionIOData data : IOData){

                        if (!data.getOutput().equals(gradeService.executeCode(requestDTO, data.getInput()))){
                            wss.sendMessage(new TextMessage("200"));
                            result = "200";
                            break;
                        }
                        wss.sendMessage(new TextMessage(count / size + ""));
                        count+=1;
                    }
                }else{
                    wss.sendMessage(new TextMessage("300"));
                    result = "300";
                }

            }catch(Exception e) {
                log.error(e.getMessage());
            }
            solveDTO.setSolveid(savedSolve.getSolveid());
            solveDTO.setSolveddate(savedSolve.getSolveddate());
            solveDTO.setSolved(result);

            if(Integer.parseInt(result) == 100){

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

