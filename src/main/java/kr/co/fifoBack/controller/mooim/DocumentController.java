package kr.co.fifoBack.controller.mooim;

import kr.co.fifoBack.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/document/upload")
    public ResponseEntity<?> uploadFile(){
        return documentService.uploadFile();
    }
}
