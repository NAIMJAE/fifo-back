package kr.co.fifoBack.service;

import com.querydsl.core.Tuple;
import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.dto.PageResponseDTO;
import kr.co.fifoBack.dto.gathering.GathCommentDTO;
import kr.co.fifoBack.dto.gathering.GatheringDTO;
import kr.co.fifoBack.dto.gathering.RecruitDTO;
import kr.co.fifoBack.dto.gathering.page.GathPageRequestDTO;
import kr.co.fifoBack.dto.gathering.page.GathPageResponseDTO;
import kr.co.fifoBack.entity.Users;
import kr.co.fifoBack.entity.gathering.GathComment;
import kr.co.fifoBack.entity.gathering.Gathering;
import kr.co.fifoBack.entity.gathering.Recruit;
import kr.co.fifoBack.mapper.GatheringMapper;
import kr.co.fifoBack.repository.gathering.GathCommentRepository;
import kr.co.fifoBack.repository.gathering.GatheringRepository;
import kr.co.fifoBack.repository.gathering.RecruitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class GatheringService {

    private final GatheringRepository gatheringRepository;
    private final GathCommentRepository gathCommentRepository;
    private final RecruitRepository recruitRepository;

    private final GatheringMapper gatheringMapper;
    private final HelperService helperService;
    private final ModelMapper modelMapper;

    // 모임 글 목록
    public ResponseEntity<?> selectGatherings(GathPageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable("gathno");

        // GatheringDTO 기본값인지 확인
        boolean isDefaultGatheringDTO = isDefaultGatheringDTO(pageRequestDTO.getGatheringDTO());

        Page<Tuple> tuples = null;

        if (isDefaultGatheringDTO) {
            // 기본값이거나 null이면 전체 조회
            tuples = gatheringRepository.selectGatherings(pageRequestDTO, pageable);
        } else {
            // 기본값이 아니면 검색 조회
            tuples = gatheringRepository.selectGatheringsByKeyword(pageRequestDTO, pageable);
        }
        List<GatheringDTO> dtoList = tuples.stream()
                .map(tuple -> {
                    GatheringDTO gatheringDTO = modelMapper.map(tuple.get(0, Gathering.class), GatheringDTO.class);
                    gatheringDTO.setUsernick(tuple.get(1, String.class));
                    gatheringDTO.setUserthumb(tuple.get(2, String.class));
                    return gatheringDTO;
                })
                .toList();

        int total = (int) tuples.getTotalElements();

        GathPageResponseDTO pageGathDTO = GathPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();

        return ResponseEntity.ok().body(pageGathDTO);
    }
    // 모임 글 보기
    @Transactional
    public ResponseEntity<?> selectGathering(int gathno){

        // 글 조회
        Tuple result = gatheringRepository.selectGathering(gathno);
        Gathering gathering = result.get(0, Gathering.class);
        GatheringDTO gatheringDTO = modelMapper.map(gathering, GatheringDTO.class);
        gatheringDTO.setUsernick(result.get(1, String.class));
        gatheringDTO.setUserthumb(result.get(2, String.class));

        // 모임 신청 현황 조회
        List<Tuple> recruits = recruitRepository.selectRecruitList(gathno);
        List<RecruitDTO> recruitList = recruits.stream()
                .map(tuple -> {
                    Recruit recruit = tuple.get(0, Recruit.class);
                    Users users = tuple.get(1, Users.class);
                    RecruitDTO recruitDTO = modelMapper.map(recruit, RecruitDTO.class);
                    recruitDTO.setNick(users.getNick());
                    recruitDTO.setRegion(users.getRegion());
                    recruitDTO.setThumb(users.getThumb());
                    return recruitDTO;
                }).toList();
        gatheringDTO.setRecruitList(recruitList);

        // 조회수 ++
        gathering.setHit(gathering.getHit() + 1);
        gatheringRepository.save(gathering);

        return ResponseEntity.ok().body(gatheringDTO);
    }
    // 모임 글 작성
    @Transactional
    public ResponseEntity<?> insertGathering(GatheringDTO gatheringDTO){

        // 썸네일 저장
        MultipartFile thumbnail = gatheringDTO.getThumbnail();
        String thumb = null;
        if (thumbnail != null && !thumbnail.isEmpty()) {
            thumb = helperService.uploadFiles(List.of(thumbnail), "gathering/thumb/", false).get(0);
            gatheringDTO.setThumb(thumb);
        }

        // DB 저장
        gatheringDTO.setGathstate("모집중");
        Gathering gathering = gatheringRepository.save(modelMapper.map(gatheringDTO, Gathering.class));

        // 이미지 주소 대체
        String replacedContent = gathering.getGathdetail().replace("$#@^", Integer.toString(gathering.getGathno()));
        gathering.setGathdetail(replacedContent);
        gatheringRepository.save(gathering);

        // 이미지 저장 (DB 저장 필요 없음)
        if (gatheringDTO.getImages() != null && !gatheringDTO.getImages().isEmpty()) {
            helperService.uploadFiles(gatheringDTO.getImages(), "gathering/images/" + gathering.getGathno() + "/", true);
        }
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

    // 내 모임 목록 조회
    public ResponseEntity<?> selectGatheringsByUser(GathPageRequestDTO gathPageRequestDTO){

        // 내 모임 글?조회???인가 프로젝트 테이블 조회인가
        // 채팅이랑 프로젝트에 포함된 게시판(노션 페이지같은거)도 같이 조회
        return ResponseEntity.ok().body("");
    }
    // 댓글 작성
    @Transactional
    public ResponseEntity<?> insertComment(GathCommentDTO commentDTO) {
        // 댓글 저장
        GathComment gathComment = gathCommentRepository.save(modelMapper.map(commentDTO, GathComment.class));

        // 모임글 댓글 수 ++
        gatheringMapper.updateGatheringCommentCount(commentDTO.getGathno());

        return ResponseEntity.ok().body(1);
    }

    // 댓글 불러오기
    public ResponseEntity<?> selectComment(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("commentno");
        Page<Tuple> tuples = gathCommentRepository.selectCommentByGathno(pageRequestDTO, pageable);

        List<GathCommentDTO> dtoList = tuples.stream()
                .map(tuple -> {
                    GathCommentDTO gathcommentDTO = modelMapper.map(tuple.get(0, GathComment.class), GathCommentDTO.class);
                    gathcommentDTO.setUsernick(tuple.get(1, String.class));
                    gathcommentDTO.setUserthumb(tuple.get(2, String.class));
                    return gathcommentDTO;
                })
                .toList();

        int total = (int) tuples.getTotalElements();

        PageResponseDTO pageGathDTO = PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();

        return ResponseEntity.ok().body(pageGathDTO);
    }

    // GatheringDTO가 기본값인지 확인하는 메서드
    private boolean isDefaultGatheringDTO(GatheringDTO gatheringDTO) {
        if (gatheringDTO == null) {
            return true;
        }
        // GatheringDTO의 필드들이 기본값인지 확인 : 기본값이면 true
        return gatheringDTO.getGathcate() == 0
                && gatheringDTO.getGathmode() == null
                && gatheringDTO.getGathtotalmember() == 0
                && gatheringDTO.getGathrecruitfield() == null
                && gatheringDTO.getGathlanguage() == null;
    }

    // 모임 신청
    @Transactional
    public ResponseEntity<?> insertRecruit(RecruitDTO recruitDTO) {
        log.info("recruitDTO : " + recruitDTO);

        Optional<Recruit> optRecruit = recruitRepository.findByUsernoAndGathno(recruitDTO.getUserno(), recruitDTO.getGathno());

        if (optRecruit.isPresent()) {
            recruitRepository.deleteById(optRecruit.get().getRecruitno());
            return ResponseEntity.status(HttpStatus.OK).body(0);
        }else {
            recruitDTO.setRecruitstate("수락 대기");
            recruitRepository.save(modelMapper.map(recruitDTO, Recruit.class));
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }
    }

    // 모임 신청 관리
    @Transactional
    public ResponseEntity<?> updateRecruit(int recruitno, String state) {
        Optional<Recruit> optRecruit = recruitRepository.findById(recruitno);
        if(optRecruit.isPresent()) {
            optRecruit.get().setRecruitstate(state);
            recruitRepository.save(optRecruit.get());
            Optional<Gathering> optGathering = gatheringRepository.findById(optRecruit.get().getGathno());

            if (optGathering.isPresent()) {
                if (state.equals("신청 거절")) {
                    if (optGathering.get().getGathnowmember() > 0) {
                        optGathering.get().setGathnowmember(optGathering.get().getGathnowmember()-1);
                        gatheringRepository.save(optGathering.get());
                    }
                }else {
                    optGathering.get().setGathnowmember(optGathering.get().getGathnowmember()+1);
                    gatheringRepository.save(optGathering.get());
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }
        return ResponseEntity.status(HttpStatus.OK).body(0);
    }

}
