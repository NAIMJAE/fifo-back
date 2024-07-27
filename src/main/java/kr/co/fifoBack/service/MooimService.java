package kr.co.fifoBack.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.co.fifoBack.dto.gathering.*;
import kr.co.fifoBack.entity.gathering.Gathering;
import kr.co.fifoBack.entity.gathering.Kanban;
import kr.co.fifoBack.entity.gathering.Mooim;
import kr.co.fifoBack.entity.gathering.MooimMember;
import kr.co.fifoBack.repository.gathering.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MooimService {
    @PersistenceContext
    private EntityManager entityManager;

    private final MooimRepository mooimRepository;
    private final GatheringRepository gatheringRepository;
    private final MooimMemberRepository mooimMemberRepository;
    private final KanbanRepository kanbanRepository;
    private final CalendarRepository calendarRepository;

    private final ModelMapper modelMapper;

    // 내 모임 목록 조회
    public ResponseEntity<?> selectMyMooims(MooimDTO mooimDTO) {
        List<?> results = null;

        if(mooimDTO.getMooimstate() == 0) {
            // 전체 조회
            results = mooimRepository.findMooimsByUserno(mooimDTO.getUserno());

        }else if(mooimDTO.getMooimstate() == 3) {
            // 모집중 -> 모임글 조회
            results = gatheringRepository.findGatheringsByUserno(mooimDTO.getUserno());
        }else {
            // 전체 조회가 아닐 때 (진행중, 완료)
            results = mooimRepository.findMooimsByUsernoAndMooimstate(mooimDTO.getUserno(), mooimDTO.getMooimstate());
        }
        List<?> dtoList = results.stream()
                .map(entity -> {
                    if(mooimDTO.getMooimstate() == 3){
                        log.info(entity.toString());
                        return modelMapper.map(entity, GatheringDTO.class);
                    }else {
                        return modelMapper.map(entity, MooimDTO.class);
                    }
                })
                .toList();

        return ResponseEntity.ok().body(dtoList);
    }

    // 모임 시작
    @Transactional
    public ResponseEntity<?> insertMooim(MooimDTO mooimDTO) {
        int gathno = mooimDTO.getGathno();

        Gathering gathering = gatheringRepository.findById(gathno).get();

        // 모임글 상태 변경
        gathering.setGathstate("모집종료");
        gatheringRepository.save(gathering);

        // 모임 생성
        mooimDTO.setMooimcate(gathering.getGathcate());
        mooimDTO.setUserno(gathering.getUserno());
        Mooim savedMooim = mooimRepository.save(modelMapper.map(mooimDTO, Mooim.class));

        int mooimno = savedMooim.getMooimno();

        // DB 새로고침
        entityManager.flush();

        // 모임 멤버 생성
        for (RecruitDTO recruit : mooimDTO.getRecruitMember()) {
            MooimMemberDTO memberDTO = new MooimMemberDTO();
            memberDTO.setUserno(recruit.getUserno());
            memberDTO.setMooimno(mooimno);
            mooimMemberRepository.save(modelMapper.map(memberDTO, MooimMember.class));
        }

        // 캘린더 생성

        // 칸반 생성
        KanbanDTO kanbanDTO = new KanbanDTO();
        kanbanDTO.setKanstatus("진행중");
        kanbanDTO.setMooimno(mooimno);
        log.info("kanban : " + kanbanDTO);
        kanbanRepository.save(modelMapper.map(kanbanDTO, Kanban.class));

        return ResponseEntity.ok().body(mooimno);
    }
}
