package kr.co.fifoBack.controller.user;

import kr.co.fifoBack.dto.user.UsersDTO;
import kr.co.fifoBack.service.TermsService;
import kr.co.fifoBack.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.dnd.DropTarget;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor

public class UserController {

    private final TermsService termsService;
    private final UserService userService;

    /** 회원가입*/
    @PostMapping("/user/register")
    public ResponseEntity<?> userRegister(@RequestBody UsersDTO usersDTO){

        return userService.register(usersDTO);

    }

    /** 로그인*/
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody UsersDTO usersDTO){
        return userService.login(usersDTO);

    }

    /**이용약관 가져오기*/
    @PostMapping("/user/terms")
    public ResponseEntity<?> getTerms(){
        return termsService.getTerms();
    }

    /**개인정보 처리방침 가져오기*/
    @PostMapping("/user/privacy")
    public ResponseEntity<?> getPrivacy(){
        return termsService.getPrivacy();
    }

    /**언어 목록 가져오기*/
    @GetMapping("/user/language")
    public ResponseEntity<?> getLanguage(){
        return userService.getLanguage();
    }



    /** 유저 정보 가져오기*/
    @GetMapping("/user/getProfile")
    public ResponseEntity<?> getProfile (@RequestParam int userno){
        log.info("회원번호" + userno);
        return userService.getProfile(userno);
    }
    
    /**내 언어 목록 중복 제거해서 가져오기*/
    @GetMapping("/user/distinctLanguage")
    public ResponseEntity<?> getDistinctLanguage(@RequestParam int userno){
        return userService.getDistinctLanguage(userno);
    }

    /**내 정보 수정*/
    @PostMapping("/user/update")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> data, @RequestParam int userno){
        String type = data.get("type");
        String information = data.get("information");
        log.info("타입@@@" + type);
        log.info("정보@@@" + information);
        log.info("번호@@@" + userno);

        return userService.updateProfile(userno, type, information);
    }

    /**내 기술 스택 추가하기*/
    @PostMapping("/user/addSkill")
    public ResponseEntity<?> addSkill(@RequestParam("userno") int userno, @RequestBody String[] inputSkill ){

        return userService.addSkill(userno, inputSkill);
    }

    /**내 기술 스택 삭제*/
    @DeleteMapping("/user/deleteSkill")
    public ResponseEntity<?> deleteSkill(@RequestParam("userno") int userno, @RequestParam("languagename") String languagename ){

        return userService.deleteSkill(userno, languagename);
    }


}
