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

    /**아이디 찾기*/
    @PostMapping("/user/findId")
    public ResponseEntity<?> findId(@RequestBody UsersDTO usersDTO){
        String name = usersDTO.getName();
        String hp = usersDTO.getHp();

        return userService.findId(name, hp);
    }

    /**비밀번호 찾기*/
    @PostMapping("/user/findPass")
    public ResponseEntity<?> findPass(@RequestBody Map<String, String> data){
        String email = data.get("email");
        String pass = data.get("pass");
        log.info(email);
        log.info(pass);
        return userService.findPass(email, pass);

    }
}

