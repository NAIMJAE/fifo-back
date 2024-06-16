package kr.co.fifoBack.controller.user;

import kr.co.fifoBack.dto.user.UsersDTO;
import kr.co.fifoBack.entity.Users;
import kr.co.fifoBack.jwt.JwtProvider;
import kr.co.fifoBack.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor

public class UserController {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    /** 로그인*/
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody UsersDTO userDTO){
        log.info("login...1" + userDTO.getEmail());
        log.info("login...2" + userDTO.getPass());

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(),userDTO.getPass());

            // db 조회
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            
            // 인증된 사용자 정보 가져오기
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            Users users = userDetails.getUsers();
            log.info("login...3" + users);

            // 토큰 발급
            String accessToken = jwtProvider.createToken(users, 1);
            String refreshToken = jwtProvider.createToken(users, 7);

            Map<String, Object> userMap = new HashMap<>();
            log.info("login...4" + userMap);

            userMap.put("email", users.getEmail());
            userMap.put("name", users.getName());
            userMap.put("nick", users.getNick());
            userMap.put("hp", users.getHp());
            userMap.put("region", users.getRegion());
            userMap.put("age", users.getAge());
            userMap.put("rdate", users.getRdate());
            userMap.put("role", users.getRole());
            userMap.put("accessToken", accessToken);
            userMap.put("refreshToken", refreshToken);

            return ResponseEntity.ok().body(userMap);
        }catch (Exception e){
            log.info("login..." + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("USER NOT FOUND");
        }

    }

}
