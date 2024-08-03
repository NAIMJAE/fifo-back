package kr.co.fifoBack.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.fifoBack.dto.grade.CodeExecutionRequestDTO;
import kr.co.fifoBack.entity.grade.QuestionIOData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

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

        for(String key : sessionMap.keySet()) {
            log.info(key);
            WebSocketSession wss = sessionMap.get(key);
            try {
                List<QuestionIOData> IOData = gradeService.selectQuestionIOData(requestDTO.getQuestionNo());

                if (!IOData.isEmpty()){
                    float count = 1;
                    float size = IOData.size();
                    for(QuestionIOData data : IOData){

                        if (!data.getOutput().equals(gradeService.executeCode(requestDTO, data.getInput()))){
                            wss.sendMessage(new TextMessage("틀렸습니다."));
                            break;
                        }
                        wss.sendMessage(new TextMessage(count / size + ""));
                        count+=1;
                    }
                }else{
                    wss.sendMessage(new TextMessage("문제 오류 입니다."));
                }

            }catch(Exception e) {
                log.error(e.getMessage());
            }
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


    public String examineCode(CodeExecutionRequestDTO request){
        List<QuestionIOData> IOData = gradeService.selectQuestionIOData(request.getQuestionNo());

        if (!IOData.isEmpty()){
            for(QuestionIOData data : IOData){
                if (!data.getOutput().equals(gradeService.executeCode(request, data.getInput()))){
                    return "틀렸습니다.";
                }
            }
            return "정답입니다.";
        }
        return "문제 오류";
    }

}

