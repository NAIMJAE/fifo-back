package kr.co.fifoBack.controller.user;

import kr.co.fifoBack.service.user.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    /**중복검사*/
    @GetMapping("/user/duplicateTest")
    public ResponseEntity<?> duplicateTest(@RequestParam("param")String param){
        log.info("여기요@@"+param);
        return emailService.duplicateTest(param);
    }
    
    /**이메일 인증코드 전송*/
    @PostMapping("/user/email-check")
    public ResponseEntity<?> emailCode(@RequestBody Map<String, String> emailMap){
        String email = emailMap.get("email");

        log.info("여여여@@@"+email);
        return emailService.emailCode(email);
    }

    /**이메일 인증코드 확인*/
    @PostMapping("/user/check-code")
    public ResponseEntity<?> checkCode(@RequestBody Map<String, String> codeMap){
        String emailCode = codeMap.get("emailCode");
        String encodedCode = codeMap.get("encodedCode");

        log.info("아 : "+emailCode);
        log.info("자 : "+encodedCode);
        return emailService.checkCode(emailCode, encodedCode);
    }
}
