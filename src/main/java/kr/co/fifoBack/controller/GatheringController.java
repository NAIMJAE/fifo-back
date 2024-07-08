package kr.co.fifoBack.controller;

import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.dto.gathering.GathCommentDTO;
import kr.co.fifoBack.dto.gathering.GatheringDTO;
import kr.co.fifoBack.dto.gathering.MooimDTO;
import kr.co.fifoBack.dto.gathering.page.GathPageRequestDTO;
import kr.co.fifoBack.dto.post.CommentDTO;
import kr.co.fifoBack.service.GatheringService;
import kr.co.fifoBack.service.MooimService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Controller
public class GatheringController {

    private final GatheringService gatheringService;
    private final MooimService mooimService;

    // 모임 글 목록
    @PostMapping("/gatherings")
    public ResponseEntity<?> selectGatherings(@RequestBody GathPageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO.toString());

        return gatheringService.selectGatherings(pageRequestDTO);
    }
    // 모임 글 보기
    @GetMapping("/gathering/{gathno}")
    public ResponseEntity<?> selectGathering(@PathVariable("gathno")  int gathno){
        log.info("gathno : " + gathno);
        return gatheringService.selectGathering(gathno);
    }
    // 모임 글 작성
    @PostMapping("/gathering")
    public ResponseEntity<?> insertGathering(GatheringDTO gatheringDTO){
        log.info("gatheringDTO : " + gatheringDTO);
        return gatheringService.insertGathering(gatheringDTO);
    }
    // 모임 글 수정
    @PutMapping("/gathering")
    public ResponseEntity<?> updateGathering(@RequestBody GatheringDTO gatheringDTO){
        return null;
    }
    // 모임 글 삭제
    @DeleteMapping("/gathering")
    public ResponseEntity<?> deleteGathering(@RequestParam("gathno") int gathno){
        return null;
    }
    // 내 모임 목록 조회
    @PostMapping("/my/gatherings")
    public ResponseEntity<?> selectMyGatherings(@RequestBody MooimDTO mooimDTO){
        log.info(mooimDTO.toString());
        return mooimService.selectMyMooims(mooimDTO);
    }
    // 댓글 작성
    @PostMapping("/gathcomment")
    public ResponseEntity<?> commentWrite(@RequestBody GathCommentDTO commentDTO) {
        log.info("commentDTO : " + commentDTO);
        return gatheringService.insertComment(commentDTO);
    }

    // 댓글 불러오기
    @GetMapping("/gathcomment")
    public ResponseEntity<?> commentView(@RequestParam("pg") int pg, @RequestParam("gathno") int gathno) {
        log.info("gathno : " + gathno);
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPg(pg);
        pageRequestDTO.setGathno(gathno);
        log.info("pageRequestDTO : " + pageRequestDTO);
        return gatheringService.selectComment(pageRequestDTO);
    }
}
