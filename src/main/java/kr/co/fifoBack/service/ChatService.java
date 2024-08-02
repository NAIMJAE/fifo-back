package kr.co.fifoBack.service;

import com.querydsl.core.Tuple;
import kr.co.fifoBack.dto.mooim.ChatDTO;
import kr.co.fifoBack.entity.mooim.Chat;
import kr.co.fifoBack.repository.mooim.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final ModelMapper modelMapper;

    // 채팅 불러오기
    public ResponseEntity<?> selectChat(int mooimno) {
        List<Tuple> chatList = chatRepository.findByMooimno(mooimno);

        List<ChatDTO> chatDTOList = chatList.stream()
                .map(each -> {
                    Chat chat = each.get(0, Chat.class);
                    String nick = each.get(1, String.class);
                    String thumb = each.get(2, String.class);
                    ChatDTO chatDTO = modelMapper.map(chat, ChatDTO.class);
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
}
