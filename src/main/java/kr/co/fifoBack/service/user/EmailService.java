package kr.co.fifoBack.service.user;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import kr.co.fifoBack.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    /**중복검사*/
    public ResponseEntity<?> duplicateTest(String param){
        int count = usersMapper.duplicateTest(param);

        if(param.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("NOT FOUND");
        }else{
            return ResponseEntity.ok().body(count);
        }
    }

    /**이메일 인증 코드전송*/
    public ResponseEntity<?> emailCode(String email){
        log.info("@@요요@@" + sender);
        log.info("@@요요@@" + email);

        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
        log.info("인증코드 : " + code);

        String strCode = String.valueOf(code);
        String encoded = Base64.getEncoder().encodeToString(strCode.getBytes());

        String title = "FIFO의 인증코드 입니다.";
        String content = "<h1>인증코드는" + strCode + " 입니다.</h1>";

        try {
            MimeMessage sendMail = javaMailSender.createMimeMessage();
            sendMail.setFrom(new InternetAddress(sender, "보내는 사람", "UTF-8"));
            sendMail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            sendMail.setSubject(title);
            sendMail.setContent(content, "text/html;charset=UTF-8");

            javaMailSender.send(sendMail);
        }catch (Exception e){
            log.info("이메일 코드 전송 오류" + e.getMessage());
        }
        return ResponseEntity.ok().body(encoded);
    }

    /**인증코드 검사*/
    public ResponseEntity<?> checkCode(String emailCode, String encodedCode){
        
        /**인코딩 된 암호 풀기*/
        String decodedCode = new String(Base64.getDecoder().decode(encodedCode));

        Map<String, Integer> result = new HashMap<>();
        /**인증 성공*/
        if(emailCode.equals(decodedCode)){
            result.put("result", 1);
            return ResponseEntity.ok().body(result);
        }else {
            result.put("result", 0);
            return ResponseEntity.ok().body(result);
        }
    }
}
