package kr.co.fifoBack.service;

import kr.co.fifoBack.dto.mooim.DocumentDTO;
import kr.co.fifoBack.entity.mooim.Document;
import kr.co.fifoBack.repository.mooim.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentService {
    private final ModelMapper modelMapper;
    private final DocumentRepository documentRepository;

    /**현재 모임의 문서 가져오기*/
    public ResponseEntity<?> selectMooimDocument(int mooimno) {
        if(mooimno<0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
        else{
            List<Document> documents = documentRepository.findByMooimnoOrderByDocnoAsc(mooimno);

            List<DocumentDTO> result = documents.stream()
                    .map(list->modelMapper.map(list, DocumentDTO.class))
                    .toList();
            log.info(result.toString());

            return ResponseEntity.ok().body(result);
        }
    }

    /**새로운 문서 생성*/
    public ResponseEntity<?> createDocument(int mooimno){
        if(mooimno<0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
        }else{
            DocumentDTO documentDTO = new DocumentDTO();
            documentDTO.setTitle("제목없음");
            documentDTO.setMooimno(mooimno);

            Document document = modelMapper.map(documentDTO, Document.class);
            Document document1 = documentRepository.save(document);

            int result = document1.getDocno();

            return ResponseEntity.ok().body(result);
        }
    }

    /**선택한 문서 내용 가져오기*/
    public ResponseEntity<?> getDocument(int docno){
        if(docno<=0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
        else{
            Optional<Document> documents = documentRepository.findById(docno);

            if(documents.isPresent()) {
                Document document = documents.get();
                DocumentDTO documentDTO = modelMapper.map(document, DocumentDTO.class);
                log.info(documentDTO.toString());
                return ResponseEntity.ok().body(documentDTO);

            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found");
            }

        }
    }

    /**문서 삭제하기*/
    public ResponseEntity<?> deleteDocument(int docno) {
        if(docno<=0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
        else{
            documentRepository.deleteById(docno);
            return ResponseEntity.ok().body(1);
        }
    }

    /**파일 업로드*/
    public ResponseEntity<?> uploadFile(){
        return null;
    }
}
