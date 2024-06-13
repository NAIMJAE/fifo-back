package kr.co.fifoBack.service;

import kr.co.fifoBack.dto.gathering.GatheringDTO;
import kr.co.fifoBack.entity.gathering.Gathering;
import kr.co.fifoBack.repository.GathCommentRepository;
import kr.co.fifoBack.repository.GatheringRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class GatheringService {

    private final GatheringRepository gatheringRepository;
    private final GathCommentRepository gathCommentRepository;

    private final HelperService helperService;
    private final ModelMapper modelMapper;

    // 모임 글 목록
    public ResponseEntity<?> selectGatherings(){

        return null;
    }
    // 모임 글 보기
    public ResponseEntity<?> selectGathering(int gathno){
        return null;
    }
    // 모임 글 작성
    public ResponseEntity<?> insertGathering(GatheringDTO gatheringDTO){

        // DB 저장
        Gathering gathering = gatheringRepository.save(modelMapper.map(gatheringDTO, Gathering.class));
        // 이미지 저장 (DB 저장 필요 없음)
        helperService.uploadFiles(gatheringDTO.getImages(), "gathering/images/", true);

        return ResponseEntity.ok().body(gathering.getGathno());
    }
    // 모임 글 수정
    public ResponseEntity<?> updateGathering(GatheringDTO gatheringDTO){
        return null;
    }
    // 모임 글 삭제
    public ResponseEntity<?> deleteGathering(int gathno){
        return null;
    }
}
