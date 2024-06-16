package kr.co.fifoBack.service;

import com.querydsl.core.Tuple;
import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.dto.gathering.GatheringDTO;
import kr.co.fifoBack.entity.gathering.Gathering;
import kr.co.fifoBack.repository.gathering.GathCommentRepository;
import kr.co.fifoBack.repository.gathering.GatheringRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class GatheringService {

    private final GatheringRepository gatheringRepository;
    private final GathCommentRepository gathCommentRepository;

    private final HelperService helperService;
    private final ModelMapper modelMapper;

    // 모임 글 목록
    public ResponseEntity<?> selectGatherings(PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable("pno");
        return null;
    }
    // 모임 글 보기
    public ResponseEntity<?> selectGathering(int gathno){
        Tuple result = gatheringRepository.selectGathering(gathno);
        GatheringDTO gatheringDTO = modelMapper.map(result.get(0, Gathering.class), GatheringDTO.class);
        gatheringDTO.setUsernick(result.get(1, String.class));
        gatheringDTO.setUserthumb(result.get(2, String.class));
        return ResponseEntity.ok().body(gatheringDTO);
    }
    // 모임 글 작성
    public ResponseEntity<?> insertGathering(GatheringDTO gatheringDTO){

        // 썸네일 저장
        MultipartFile thumbnail = gatheringDTO.getThumbnail();
        String thumb = null;
        if (thumbnail != null && !thumbnail.isEmpty()) {
            thumb = helperService.uploadFiles(List.of(thumbnail), "gathering/thumb/", true).get(0);
            gatheringDTO.setThumb(thumb);
        }

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
