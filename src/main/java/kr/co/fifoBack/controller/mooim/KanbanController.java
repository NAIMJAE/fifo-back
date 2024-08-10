package kr.co.fifoBack.controller.mooim;

import kr.co.fifoBack.dto.gathering.KanbanDTO;
import kr.co.fifoBack.dto.mooim.ChatDTO;
import kr.co.fifoBack.service.KanbanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class KanbanController {

    private final KanbanService kanbanService;

    // 칸반 조회
    @GetMapping("/kanban/{mooimno}")
    public ResponseEntity<?> selectKanban(@PathVariable("mooimno") int mooimno) {
        log.info("mooimno : " + mooimno);

        return kanbanService.selectKanban(mooimno);
    }
    
    // 칸반 아이템 생성, 수정
    @PostMapping("/kanban")
    public ResponseEntity<?> insertKanban(@RequestBody KanbanDTO kanbanDTO) {
        log.info("kanbanDTO : " + kanbanDTO);

        return kanbanService.insertKanban(kanbanDTO);
    }
}
