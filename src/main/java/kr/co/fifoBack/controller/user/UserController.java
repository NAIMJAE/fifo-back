package kr.co.fifoBack.controller.user;

import kr.co.fifoBack.dto.user.UsersDTO;
import kr.co.fifoBack.entity.Users;
import kr.co.fifoBack.jwt.JwtProvider;
import kr.co.fifoBack.repository.user.UserRepository;
import kr.co.fifoBack.security.MyUserDetails;
import kr.co.fifoBack.service.TermsService;
import kr.co.fifoBack.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.apache.ibatis.annotations.Delete;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
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

    /**중복검사*/
    @GetMapping("/user/duplicateTest")
    public ResponseEntity<?> duplicateTest(@RequestParam("param")String param){
        log.info("여기요@@"+param);
        return userService.duplicateTest(param);
    }
}
