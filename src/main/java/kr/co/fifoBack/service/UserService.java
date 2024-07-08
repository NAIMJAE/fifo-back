package kr.co.fifoBack.service;

import kr.co.fifoBack.dto.grade.LanguageDTO;
import kr.co.fifoBack.dto.user.UsersDTO;
import kr.co.fifoBack.entity.Users;
import kr.co.fifoBack.entity.grade.Language;
import kr.co.fifoBack.jwt.JwtProvider;
import kr.co.fifoBack.repository.grade.LanguageRepository;
import kr.co.fifoBack.repository.user.UserRepository;
import kr.co.fifoBack.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final ModelMapper modelMapper;
    private final LanguageRepository languageRepository;

    /** 회원가입 */
    public ResponseEntity<?> register(UsersDTO usersDTO){
        log.info("회원가입" + usersDTO);

        if(usersDTO.getEmail().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR");
        }else{
            String encoded = passwordEncoder.encode(usersDTO.getPass());
            usersDTO.setPass(encoded);
            usersDTO.setRegion("부산");

            Users users = modelMapper.map(usersDTO, Users.class);
            userRepository.save(users);

            return ResponseEntity.ok().body(users);
        }
    }

    public ResponseEntity<?> login(UsersDTO usersDTO){
        log.info("login...1" + usersDTO.getEmail());
        log.info("login...2" + usersDTO.getPass());

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(usersDTO.getEmail(),usersDTO.getPass());

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

            userMap.put("userno", users.getUserno());
            userMap.put("nick", users.getNick());
            userMap.put("thumb", users.getThumb());
            userMap.put("role", users.getRole());
            userMap.put("accessToken", accessToken);
            userMap.put("refreshToken", refreshToken);

            log.info("login...4" + userMap);

            return ResponseEntity.ok().body(userMap);
        }catch (Exception e){
            log.info("login..." + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("USER NOT FOUND");
        }
    }

    public ResponseEntity<?> getLanguage(){
        List<Language> languages = languageRepository.findAll();

        if(languages.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }else{
            List<LanguageDTO> result = languages.stream()
                    .map(language -> modelMapper.map(language, LanguageDTO.class))
                    .collect(Collectors.toList());
            log.info(result.toString());
            return ResponseEntity.ok().body(result);
        }
    }
}
