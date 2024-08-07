package kr.co.fifoBack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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

        // 파일 폴더 자동 생성
        File checkPath = new File(path);
        if (!checkPath.exists()) {
            checkPath.mkdirs();  // mkdir() 대신 mkdirs() 사용해서 중간 경로도 생성
        }

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

    /*
        파일 삭제
        - location : 삭제할 파일이 저장되어 있는 주소
        - fileSavedName : 저장된 파일의 이름
    */
    public boolean deleteFiles(String location, String fileSavedName) {
        String filePath = fileUploadPath + "/" + location;

        File file = new File(filePath + File.separator + fileSavedName);
        if (file.exists()) {
            // 파일 삭제 시도
            if (file.delete()) {
                log.info("파일 삭제 성공: " + fileSavedName);
                return true;
            } else {
                log.info("파일 삭제 실패: " + fileSavedName);
                return false;
            }
        } else {
            log.info("파일이 존재하지 않음: " + fileSavedName);
            return false;
        }
    }
    
    /*
        파일 디렉토리 삭제
        - location : 삭제할 디렉토리 주소

        uploads에 저장된 파일을 디렉토리 단위로 삭제하는 매서드
     */
    public boolean deleteFileDirectory(String location) {
        File folder = new File(fileUploadPath + File.separator + location);

        try {
            while(folder.exists()) {
                File[] fileList = folder.listFiles(); //파일리스트 얻어오기

                for (int i = 0; i < fileList.length; i++) {
                    fileList[i].delete(); //파일 삭제
                }

                if(fileList.length == 0 && folder.isDirectory()){
                    folder.delete(); //대상폴더 삭제
                }
            }
            return true;
        } catch (Exception e) {
            e.getStackTrace();
            return false;
        }
    }

    /*
        파일 다운로드
        - location : 다운로드할 파일의 디렉토리 위치
        - fileName : 다운로드할 파일의 저장된 이름 (sName)
        - downloadName : 다운로드시 사용할 파일 이름 (oName)

        주소 + 위치 를 통해 다운로드할 파일이 저장된 경로 생성
        생성된 경로와 파일 이름을 통해 파일의 완전한 경로를 나타내는 path 객체 생성
        path 객체를 이용해 파일에 접근할 수 있는 URL 생성 (Resource 객체)
        Resource 객체가 유효한지 확인 후 반환

        ResponseEntity 설명
        - Content-Disposition: attachment -> 브라우저에게 응답 콘텐츠를 파일로 다운로드하게 지시
        - filename="..." -> 다운로드하는 파일의 이름 지정
     */
    public ResponseEntity<?> fileDownloadHelp(String location, String fileName, String downloadName) {
        try {
            File path = new File(fileUploadPath + File.separator + location);
            Path filePath = Paths.get(path.getAbsolutePath(), fileName);

            // 파일 경로를 URI로 변환하고 UrlResource 객체 생성
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
            }

            // response의 contentType 설정
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            // 파일 이름이 한글인 경우를 대비해 인코딩
            String encodedFileName = URLEncoder.encode(downloadName, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");

            // response의 header 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename(encodedFileName)
                    .build());
            headers.add(HttpHeaders.CONTENT_TYPE, contentType);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("파일 다운로드 실패 : " + e.getMessage());
        }
    }

    // java.util.Date -> LocalDate
    public LocalDate convertToLocalDate(Object dateObj) {

        if (dateObj instanceof java.util.Date) {
            return new java.sql.Date(((java.util.Date) dateObj).getTime()).toLocalDate();
        }
        return null;
    }

}
