package kr.co.fifoBack.controller.mooim;

import kr.co.fifoBack.dto.mooim.DocumentDTO;
import kr.co.fifoBack.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class DocumentController {
    private final DocumentService documentService;

    /**현재 모임의 문서 가져오기*/
    @GetMapping("/document/selectDocument")
    public ResponseEntity<?> selectMooimDocument(@RequestParam int mooimno) {
        return documentService.selectMooimDocument(mooimno);
    }

    /**새로운 문서 생성*/
    @PostMapping("/document/create")
    public ResponseEntity<?> createDocument(@RequestParam int mooimno) {

        log.info("mooimno : " + mooimno);
        return documentService.createDocument(mooimno);
    }

    /**선택한 문서의 내용 가져오기*/
    @GetMapping("/document/getDocument")
    public ResponseEntity<?> getDocument(@RequestParam int docno){
        return documentService.getDocument(docno);
    }

    /**문서 삭제*/
    @DeleteMapping("/document/delete")
    public ResponseEntity<?> deleteDocument(@RequestParam int docno){
        log.info("docno:" + docno);
        return documentService.deleteDocument(docno);
    }

    /**파일 저장*/
    @GetMapping("/document/upload")
    public ResponseEntity<?> uploadFile(){
        return documentService.uploadFile();
    }
}
