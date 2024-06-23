package kr.co.fifoBack.controller;

import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.dto.gathering.GatheringDTO;
import kr.co.fifoBack.dto.gathering.page.GathPageRequestDTO;
import kr.co.fifoBack.service.GatheringService;
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

    // 모임 글 목록
    @PostMapping("/gatherings")
    public ResponseEntity<?> selectGatherings(@RequestBody GathPageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO.toString());

        return gatheringService.selectGatherings(pageRequestDTO);
    }
    // 모임 글 보기
    @GetMapping("/gathering")
    public ResponseEntity<?> selectGathering(@RequestParam("gathno") int gathno){
        return gatheringService.selectGathering(gathno);
    }
    // 모임 글 작성
    @PostMapping("/gathering")
    public ResponseEntity<?> insertGathering(@RequestBody GatheringDTO gatheringDTO){
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
}
