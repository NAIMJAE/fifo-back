package kr.co.fifoBack.service;

import com.querydsl.core.Tuple;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.dto.PageResponseDTO;
import kr.co.fifoBack.dto.gathering.GathCommentDTO;
import kr.co.fifoBack.dto.gathering.GatheringDTO;
import kr.co.fifoBack.dto.gathering.RecruitDTO;
import kr.co.fifoBack.dto.gathering.page.GathPageRequestDTO;
import kr.co.fifoBack.dto.gathering.page.GathPageResponseDTO;
import kr.co.fifoBack.dto.user.SkillDTO;
import kr.co.fifoBack.dto.user.UserRegionDTO;
import kr.co.fifoBack.entity.Users;
import kr.co.fifoBack.entity.gathering.GathComment;
import kr.co.fifoBack.entity.gathering.Gathering;
import kr.co.fifoBack.entity.gathering.Recruit;
import kr.co.fifoBack.entity.post.PostHit;
import kr.co.fifoBack.entity.user.Skill;
import kr.co.fifoBack.entity.user.UserRegion;
import kr.co.fifoBack.mapper.GatheringMapper;
import kr.co.fifoBack.repository.gathering.GathCommentRepository;
import kr.co.fifoBack.repository.gathering.GatheringRepository;
import kr.co.fifoBack.repository.gathering.RecruitRepository;
import kr.co.fifoBack.repository.post.PostHitRepository;
import kr.co.fifoBack.repository.user.SkillRepository;
import kr.co.fifoBack.repository.user.UserRegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class GatheringService {
    private final HttpServletRequest httpServletRequest;
    private final GatheringRepository gatheringRepository;
    private final GathCommentRepository gathCommentRepository;
    private final RecruitRepository recruitRepository;
    private final SkillRepository skillRepository;
    private final UserRegionRepository userRegionRepository;
    private final PostHitRepository postHitRepository;

    private final GatheringMapper gatheringMapper;
    private final HelperService helperService;
    private final ModelMapper modelMapper;

    // 모임 글 목록
    public ResponseEntity<?> selectGatherings(GathPageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable("gathno");

        // GatheringDTO 기본값인지 확인
        boolean isDefaultGatheringDTO = isDefaultGatheringDTO(pageRequestDTO.getGatheringDTO());

        // 모집 중만 보기인지 확인
        boolean state = pageRequestDTO.isGathState();

        Page<Tuple> tuples = null;

        if (isDefaultGatheringDTO) {
            if(state){
                // 모집중 조회
                tuples = gatheringRepository.selectGatheringsByState(pageRequestDTO, pageable);
            }else{
                // 기본값이거나 null이면 전체 조회
                tuples = gatheringRepository.selectGatherings(pageRequestDTO, pageable);
            }
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

        // 조회수 +1
        String ipAddress = httpServletRequest.getRemoteAddr();
        Optional<PostHit> optPostHit = postHitRepository.findByGathnoAndAddress(gathno, ipAddress);
        GatheringDTO gatheringDTO = new GatheringDTO();
        // 오늘 조회한 적이 없는 사용자면
        if (optPostHit.isEmpty()) {
            // 글 조회 + 조회수++
            List<Object[]> result = gatheringRepository.selectGatheringAndHitUp(gathno);
            Object[] row = result.get(0);

            Gathering gathering = Gathering.builder()
                    .gathcate((Integer) row[0])
                    .gathcomment((Integer) row[1])
                    .gathno((Integer) row[2])
                    .gathnowmember((Integer) row[3])
                    .gathtotalmember((Integer) row[4])
                    .hit((Integer) row[5])
                    .recruitend(helperService.convertToLocalDate(row[6]))
                    .recruitstart(helperService.convertToLocalDate(row[7]))
                    .userno((Integer) row[8])
                    .gathdetail((String) row[9])
                    .gathlanguage((String) row[10])
                    .gathmode((String) row[11])
                    .gathrecruitfield((String) row[12])
                    .gathstate((String) row[13])
                    .gathtitle((String) row[14])
                    .thumb((String) row[15])
                    .modiDate(row[16] != null ? ((Timestamp) row[16]).toLocalDateTime() : null)
                    .mooimperiod((String) row[17])
                    .build();

            gatheringDTO = modelMapper.map(gathering, GatheringDTO.class);
            gatheringDTO.setUsernick((String) row[18]);
            gatheringDTO.setUserthumb((String) row[19]);

            // 조회한 사용자 목록에 추가
            PostHit postHit = new PostHit();
            postHit.setAddress(ipAddress);
            postHit.setGathno(gathno);
            postHitRepository.save(postHit);
        }else{
            Tuple result = gatheringRepository.selectGatheringAndWriter(gathno);
            gatheringDTO = modelMapper.map(result.get(0, Gathering.class), GatheringDTO.class);
            gatheringDTO.setUsernick(result.get(1, String.class));
            gatheringDTO.setUserthumb(result.get(2, String.class));
        }


        // 모임 신청 현황 조회
        List<Tuple> recruits = recruitRepository.selectRecruitList(gathno);
        List<RecruitDTO> recruitList = recruits.stream()
                .map(tuple -> {
                    Recruit recruit = tuple.get(0, Recruit.class);
                    Users users = tuple.get(1, Users.class);
                    RecruitDTO recruitDTO = modelMapper.map(recruit, RecruitDTO.class);
                    recruitDTO.setNick(users.getNick());
                    recruitDTO.setThumb(users.getThumb());
                    recruitDTO.setStack(users.getStack());

                    List<UserRegion> userRegions = userRegionRepository.findByUserno(users.getUserno());
                    List<UserRegionDTO> userRegionDTOS = userRegions.stream().map(
                            regin -> modelMapper.map(regin, UserRegionDTO.class)
                    ).toList();
                    recruitDTO.setUserRegions(userRegionDTOS);

                    List<Skill> skillList = skillRepository.findByUserno(users.getUserno());
                    List<SkillDTO> skillDTOS = skillList.stream().map(
                            skill -> modelMapper.map(skill, SkillDTO.class)
                    ).toList();
                    recruitDTO.setSkill(skillDTOS);

                    return recruitDTO;
                }).toList();
        gatheringDTO.setRecruitList(recruitList);

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
        Optional<Gathering> optGathering = gatheringRepository.findById(gatheringDTO.getGathno());

        if(optGathering.isPresent()) {
            String replacedContent = gatheringDTO.getGathdetail().replace("$#@^", Integer.toString(optGathering.get().getGathno()));
            optGathering.get().setGathdetail(replacedContent);
            optGathering.get().setGathtitle(gatheringDTO.getGathtitle());
            optGathering.get().setModiDate(LocalDateTime.now());

            // 썸네일 변경했으면
            MultipartFile thumbnail = gatheringDTO.getThumbnail();
            if (thumbnail != null && !thumbnail.isEmpty()) {
                // 기존 썸네일 있었으면 파일 삭제
                if (optGathering.get().getThumb() != null) {
                    log.info("바꿈 : " + optGathering.get().getThumb());
                    // 기존 썸네일 삭제
                    helperService.deleteFiles("/gathering/thumb/" , optGathering.get().getThumb());
                }
                // 썸네일 저장
                String thumb = helperService.uploadFiles(List.of(thumbnail), "gathering/thumb/", false).get(0);
                optGathering.get().setThumb(thumb);
            }

            Gathering savedGathering = gatheringRepository.save(optGathering.get());

            // 이미지 저장 (DB 저장 필요 없음)
            if (gatheringDTO.getImages() != null && !gatheringDTO.getImages().isEmpty()) {
                helperService.uploadFiles(gatheringDTO.getImages(), "gathering/images/" + gatheringDTO.getGathno() + "/", true);
            }
        }

        return ResponseEntity.ok().body(gatheringDTO.getGathno());
    }

    // 모임 글 삭제
    @Transactional
    public ResponseEntity<?> deleteGathering(int gathno){
        Optional<Gathering> optGathering = gatheringRepository.findById(gathno);

        if(optGathering.isPresent()) {
            // 지원 목록 삭제
            recruitRepository.deleteByGathno(gathno);

            // 썸네일 이미지 삭제
            if (optGathering.get().getThumb() != null) {
                helperService.deleteFiles("/gathering/thumb/", optGathering.get().getThumb());
            }

            // 게시글 이미지 삭제
            helperService.deleteFileDirectory("gathering/images/" + gathno);

            // 모임글 삭제
            gatheringRepository.deleteById(gathno);
        }
        return ResponseEntity.status(HttpStatus.OK).body(1);
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

    // 댓글 수정
    @Transactional
    public ResponseEntity<?> modifyComment(GathCommentDTO commentDTO) {
        Optional<GathComment> optComment = gathCommentRepository.findById(commentDTO.getCommentno());

        if (optComment.isPresent()) {
            optComment.get().setContent(commentDTO.getContent());
            optComment.get().setUpdateDate(LocalDateTime.now());
            gathCommentRepository.save(optComment.get());
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // 댓글 삭제
    public ResponseEntity<?> deleteComment(int commentno) {
        Optional<GathComment> optComment = gathCommentRepository.findById(commentno);

        if (optComment.isPresent()) {
            optComment.get().setContent("삭제되었습니다.");
            optComment.get().setUpdateDate(LocalDateTime.now());
            optComment.get().setState(1);
            gathCommentRepository.save(optComment.get());
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
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
            Optional<Gathering> optGathering = gatheringRepository.findById(optRecruit.get().getGathno());

            if (optGathering.isPresent()) {
                if (optGathering.get().getGathnowmember() >= optGathering.get().getGathtotalmember()) {
                    return ResponseEntity.status(HttpStatus.OK).body(0);
                }else if (state.equals("신청 수락")) {
                    optRecruit.get().setRecruitstate(state);
                    recruitRepository.save(optRecruit.get());
                    optGathering.get().setGathnowmember(optGathering.get().getGathnowmember()+1);
                    gatheringRepository.save(optGathering.get());
                    return ResponseEntity.status(HttpStatus.OK).body(1);
                }else {
                    return ResponseEntity.status(HttpStatus.OK).body(0);
                }
            }else {
                return ResponseEntity.status(HttpStatus.OK).body(0);
            }
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(0);
        }
    }

    // 모임 신청 모달 정보 불러오기
    public ResponseEntity<?> selectUserInfo(int userno) {
        List<Skill> skillList = skillRepository.findByUserno(userno);
        List<UserRegion> regionList = userRegionRepository.findByUserno(userno);

        Map<String, Object> result = new HashMap<>();
        result.put("skill", skillList);
        result.put("region", regionList);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 모임 시작 모달 정보 불러오기
    public ResponseEntity<?> selectGathStart(int gathno) {

        // 모임 멤버
        List<Tuple> recruits = recruitRepository.selectRecruitStart(gathno, "신청 수락");
        List<RecruitDTO> recruitList = recruits.stream()
                .map(tuple -> {
                    Recruit recruit = tuple.get(0, Recruit.class);
                    Users users = tuple.get(1, Users.class);
                    RecruitDTO recruitDTO = modelMapper.map(recruit, RecruitDTO.class);
                    recruitDTO.setNick(users.getNick());
                    recruitDTO.setThumb(users.getThumb());
                    recruitDTO.setStack(users.getStack());

                    List<UserRegion> userRegions = userRegionRepository.findByUserno(users.getUserno());
                    List<UserRegionDTO> userRegionDTOS = userRegions.stream().map(
                            regin -> modelMapper.map(regin, UserRegionDTO.class)
                    ).toList();
                    recruitDTO.setUserRegions(userRegionDTOS);

                    List<Skill> skillList = skillRepository.findByUserno(users.getUserno());
                    List<SkillDTO> skillDTOS = skillList.stream().map(
                            skill -> modelMapper.map(skill, SkillDTO.class)
                    ).toList();
                    recruitDTO.setSkill(skillDTOS);

                    return recruitDTO;
                }).toList();

        return ResponseEntity.status(HttpStatus.OK).body(recruitList);
    }
}
