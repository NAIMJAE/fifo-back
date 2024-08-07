package kr.co.fifoBack.controller.mooim;

import kr.co.fifoBack.dto.mooim.ChatDTO;
import kr.co.fifoBack.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    // 채팅 불러오기
    @GetMapping("/chatting/{mooimno}")
    public ResponseEntity<?> selectChat(@PathVariable("mooimno") int mooimno) {
        log.info("mooimno : " + mooimno);

        return chatService.selectChat(mooimno);
    }
    
    // 채팅 저장
    @PostMapping("/chatting")
    public ResponseEntity<?> saveChat(@RequestBody ChatDTO chatDTO) {
        log.info("chatDTO : " + chatDTO);

        return chatService.saveChat(chatDTO);
    }
    
    // 첨부파일 채팅 저장
    @PutMapping("/chatting")
    public ResponseEntity<?> saveFileChat(ChatDTO chatDTO) {
        log.info("chatDTO : " + chatDTO);

        return chatService.saveFileChat(chatDTO);
    }

    // 첨부파일 다운로드
    @PostMapping("/chatting/download")
    public ResponseEntity<?> downloadFileChat(@RequestBody Map<String, String> msgData) {
        log.info("msgData : " + msgData);

        return chatService.downloadFileChat(msgData);
    }
}
