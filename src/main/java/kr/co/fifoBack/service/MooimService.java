package kr.co.fifoBack.service;

import kr.co.fifoBack.dto.gathering.GatheringDTO;
import kr.co.fifoBack.dto.gathering.MooimDTO;
import kr.co.fifoBack.entity.gathering.Mooim;
import kr.co.fifoBack.repository.gathering.GatheringRepository;
import kr.co.fifoBack.repository.gathering.MooimRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MooimService {

    private final MooimRepository mooimRepository;
    private final GatheringRepository gatheringRepository;
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
                        return modelMapper.map(entity, GatheringDTO.class);
                    }else {
                        return modelMapper.map(entity, MooimDTO.class);
                    }
                })
                .toList();

        return ResponseEntity.ok().body(dtoList);
    }
}
