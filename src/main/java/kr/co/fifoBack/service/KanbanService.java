package kr.co.fifoBack.service;

import kr.co.fifoBack.dto.gathering.KanbanDTO;
import kr.co.fifoBack.entity.gathering.Kanban;
import kr.co.fifoBack.entity.gathering.Mooim;
import kr.co.fifoBack.repository.gathering.KanbanRepository;
import kr.co.fifoBack.repository.gathering.MooimRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class KanbanService {

    private final KanbanRepository kanbanRepository;
    private final MooimRepository mooimRepository;
    private final ModelMapper modelMapper;
    
    // 칸반 조회
    public ResponseEntity<?> selectKanban(int mooimno) {
        Optional<Kanban> optKanban = kanbanRepository.findByMooimno(mooimno);

        if (optKanban.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(optKanban, KanbanDTO.class));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }
    
    // 칸반 아이템 저장
    public ResponseEntity<?> insertKanban(KanbanDTO kanbanDTO) {

        Optional<Mooim> optMooim = mooimRepository.findById(kanbanDTO.getMooimno());
        if (optMooim.isPresent()) {
            optMooim.get().setProgress(kanbanDTO.getProgress());
            mooimRepository.save(optMooim.get());
        }

        Kanban kanban = kanbanRepository.save(modelMapper.map(kanbanDTO, Kanban.class));

        if (kanban.getKanno() != 0) {
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }
}
