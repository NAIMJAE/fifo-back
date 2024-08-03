package kr.co.fifoBack.controller.user;

import kr.co.fifoBack.service.user.UserProfileService;
import kr.co.fifoBack.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserProfileControlelr {

    private final UserService userService;
    private final UserProfileService userProfileService;

    /** 유저 정보 가져오기*/
    @GetMapping("/profile/select")
    public ResponseEntity<?> getProfile (@RequestParam int userno){
        log.info("회원번호" + userno);
        return userProfileService.getProfile(userno);
    }

    /**내 언어 목록 중복 제거해서 가져오기*/
    @GetMapping("/profile/distinctLanguage")
    public ResponseEntity<?> getDistinctLanguage(@RequestParam int userno){
        return userProfileService.getDistinctLanguage(userno);
    }

    /**내 정보 수정*/
    @PostMapping("/profile/update")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> data, @RequestParam int userno){
        String type = data.get("type");
        String information = data.get("information");
        log.info("타입@@@" + type);
        log.info("정보@@@" + information);
        log.info("번호@@@" + userno);

        return userProfileService.updateProfile(userno, type, information);
    }

    /**프로필 사진 수정*/
    @PostMapping("/profile/upload")
    public ResponseEntity<?> uploadProfile(@RequestBody MultipartFile file, @RequestParam int userno) throws IOException {
        log.info("@@여 보소 " + file);
        log.info("@@여 보소 " + userno);
        return userProfileService.uploadProfile(file, userno);
    }

    /**지역 가져오기*/
    @GetMapping("/profile/selectRegion")
    public ResponseEntity<?> selectRegion(){
        return userProfileService.selectRegion();
    }

    /**내 지역 가져오기*/
    @GetMapping("/profile/selectMyRegion")
    public ResponseEntity<?> selectMyRegion(@RequestParam int userno){
        return userProfileService.selectMyRegion(userno);
    }
    /**내 지역 변경하기*/
    @PostMapping("/profile/updateRegion")
    public ResponseEntity<?> addRegion(@RequestParam("userno") int userno, @RequestBody String[] regionArr) {
        log.info("컨트롤러@"+Arrays.toString(regionArr));
        return userProfileService.addRegion(userno, regionArr);
    }

    /**내 기술 스택 추가하기*/
    @PostMapping("/profile/addSkill")
    public ResponseEntity<?> addSkill(@RequestParam("userno") int userno, @RequestBody String[] inputSkill ){

        return userProfileService.addSkill(userno, inputSkill);
    }

    /**내 기술 스택 삭제*/
    @DeleteMapping("/profile/deleteSkill")
    public ResponseEntity<?> deleteSkill(@RequestParam("userno") int userno, @RequestParam("languagename") String languagename ){

        return userProfileService.deleteSkill(userno, languagename);
    }
}
