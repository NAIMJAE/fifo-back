package kr.co.fifoBack.service;

import com.google.gson.Gson;
import com.querydsl.core.Tuple;
import kr.co.fifoBack.dto.mooim.ChatDTO;
import kr.co.fifoBack.entity.mooim.Chat;
import kr.co.fifoBack.entity.post.File;
import kr.co.fifoBack.repository.mooim.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final HelperService helperService;
    private final ModelMapper modelMapper;
    private final Gson gson;

    // 채팅 불러오기
    public ResponseEntity<?> selectChat(int mooimno) {
        List<Tuple> chatList = chatRepository.findByMooimno(mooimno);

        List<ChatDTO> chatDTOList = chatList.stream()
                .map(each -> {
                    Chat chat = each.get(0, Chat.class);
                    String nick = each.get(1, String.class);
                    String thumb = each.get(2, String.class);
                    ChatDTO chatDTO = modelMapper.map(chat, ChatDTO.class);
                    chatDTO.setMsgData(gson.fromJson(chatDTO.getMessage(), Map.class));
                    chatDTO.setNick(nick);
                    chatDTO.setThumb(thumb);
                    return chatDTO;
                }).toList();
        return ResponseEntity.status(HttpStatus.OK).body(chatDTOList);
    }
    
    // 채팅 저장
    public ResponseEntity<?> saveChat(ChatDTO chatDTO) {
        Chat savedChat = chatRepository.save(modelMapper.map(chatDTO, Chat.class));
        if (savedChat.getChatno() != 0) {
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // 채팅 첨부파일 저장
    public ResponseEntity<?> saveFileChat(ChatDTO chatDTO) {
        if (chatDTO.getFile() != null) {
            List<String> filesNames = helperService.uploadFiles(chatDTO.getFile(), "/chat/files/" + chatDTO.getMooimno() + "/", false);

            String fileType = chatDTO.getFile().get(0).getContentType().split("/")[0];

            Map<String, String> msgData = new HashMap<>();
            msgData.put("type", fileType);
            msgData.put("data", filesNames.get(0));
            msgData.put("name", chatDTO.getFile().get(0).getOriginalFilename());
            msgData.put("mooimno", String.valueOf(chatDTO.getMooimno()));

            String json = gson.toJson(msgData);
            log.info(json);

            chatDTO.setMessage(json);
            Chat savedChat = chatRepository.save(modelMapper.map(chatDTO, Chat.class));

            if (savedChat.getChatno() != 0) {
                return ResponseEntity.status(HttpStatus.OK).body(msgData);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }
    
    // 채팅 첨부파일 다운로드
    public ResponseEntity<?> downloadFileChat(Map<String,String> msgData) {

        return helperService.fileDownloadHelp("chat/files/" + msgData.get("mooimno"), msgData.get("data"), msgData.get("name"));
    }
}
