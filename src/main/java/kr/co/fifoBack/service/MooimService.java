package kr.co.fifoBack.service;

import com.querydsl.core.Tuple;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.co.fifoBack.dto.gathering.*;
import kr.co.fifoBack.dto.user.SkillDTO;
import kr.co.fifoBack.dto.user.UserRegionDTO;
import kr.co.fifoBack.dto.user.UsersDTO;
import kr.co.fifoBack.entity.Users;
import kr.co.fifoBack.entity.gathering.*;
import kr.co.fifoBack.entity.user.Skill;
import kr.co.fifoBack.entity.user.UserRegion;
import kr.co.fifoBack.repository.gathering.*;
import kr.co.fifoBack.repository.user.SkillRepository;
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
    private final SkillRepository skillRepository;

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
        // 모임 팀장
        MooimMemberDTO memberDTO = new MooimMemberDTO();
        memberDTO.setUserno(mooimDTO.getUserno());
        memberDTO.setMooimno(mooimno);
        mooimMemberRepository.save(modelMapper.map(memberDTO, MooimMember.class));

        // 캘린더 생성

        // 칸반 생성
        KanbanDTO kanbanDTO = new KanbanDTO();
        kanbanDTO.setKanstatus("진행중");
        kanbanDTO.setMooimno(mooimno);
        log.info("kanban : " + kanbanDTO);
        kanbanRepository.save(modelMapper.map(kanbanDTO, Kanban.class));

        return ResponseEntity.ok().body(mooimno);
    }

    // 모임 조회
    public ResponseEntity<?> selectMooim(int mooimno) {
        Mooim mooim = mooimRepository.findById(mooimno).get();

        // 모임 멤버
        List<Tuple> results = mooimMemberRepository.selectMooimMemberAndSkills(mooimno);
        List<MooimMemberDTO> memberList = results.stream()
                .map(tuple -> {
                    MooimMember mooimMember = tuple.get(0, MooimMember.class);
                    Users users = tuple.get(1, Users.class);

                    MooimMemberDTO mooimmemberDTO = modelMapper.map(mooimMember, MooimMemberDTO.class);
                    UsersDTO usersDTO = modelMapper.map(users, UsersDTO.class);

                    // 멤버별 스킬 불러오기
                    List<Skill> skillList = skillRepository.findByUserno(users.getUserno());
                    List<SkillDTO> skillDTOS = skillList.stream().map(
                            skill -> modelMapper.map(skill, SkillDTO.class)
                    ).toList();
                    usersDTO.setSkillList(skillDTOS);

                    mooimmemberDTO.setUsersDTO(usersDTO);

                    return mooimmemberDTO;
                }).toList();

        MooimDTO mooimDTO = modelMapper.map(mooim, MooimDTO.class);
        mooimDTO.setMemberList(memberList);

        return ResponseEntity.ok().body(mooimDTO);
    }
}
