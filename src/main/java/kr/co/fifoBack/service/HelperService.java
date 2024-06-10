package kr.co.fifoBack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class HelperService {
    // 여러 Service에서 공통적으로 사용 가능한 메서드를 관리하는 Service

    /*
        파일 저장
        - files : 저장할 파일 객체 (1개여로 List로 보내기)
        - location : 저장할 위치 (fileUploadPath 는 "uploads/"까지)
                    ex) String location = "images/post"
        - name : 파일을 저장할 이름 (sName) 생성 여부 (프론트에서 만들어 오면 true)
        
        파일을 저장하고 sName이 들어 있는 List를 반환하기 때문에 DB 저장은 따로 해야함
    */
    @Value("${file.upload.path}")
    private String fileUploadPath;
    public List<String> uploadFiles(List<MultipartFile> files, String location, boolean name) {

        String path = new File(fileUploadPath).getAbsolutePath() + "/" + location;

        List<String> savedSName = new ArrayList<>();
        for (MultipartFile file : files) {
            String sName = null;
            if (!name) {
                // sName 만들기
                String oName = file.getOriginalFilename();
                String ext = oName.substring(oName.lastIndexOf("."));
                sName = UUID.randomUUID().toString()+ext;
            }else {
                // 프론트에서 만들어온 sName 사용
                sName = file.getOriginalFilename();
            }
            try {
                file.transferTo(new File(path, sName));
                savedSName.add(sName);
            }catch (IOException e) {
                log.error("fileUpload err : " + e.getMessage());
            }
        }
        return savedSName;
    }

}
