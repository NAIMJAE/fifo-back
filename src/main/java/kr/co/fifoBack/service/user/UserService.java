
package kr.co.fifoBack.service.user;

import com.querydsl.core.types.dsl.StringPath;
import jakarta.transaction.Transactional;
import kr.co.fifoBack.dto.grade.LanguageDTO;
import kr.co.fifoBack.dto.user.SkillDTO;
import kr.co.fifoBack.dto.user.UsersDTO;
import kr.co.fifoBack.entity.Users;
import kr.co.fifoBack.entity.grade.Language;
import kr.co.fifoBack.entity.user.Skill;
import kr.co.fifoBack.jwt.JwtProvider;
import kr.co.fifoBack.mapper.UsersMapper;
import kr.co.fifoBack.repository.grade.LanguageRepository;
import kr.co.fifoBack.repository.user.SkillRepository;
import kr.co.fifoBack.repository.user.UserRepository;
import kr.co.fifoBack.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.management.LockInfo;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final ModelMapper modelMapper;
    private final LanguageRepository languageRepository;

    /** 회원가입 */
    @Transactional
    public ResponseEntity<?> register(UsersDTO usersDTO){
        log.info("회원가입" + usersDTO);

        if(usersDTO.getEmail().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR");
        }else{
            String encoded = passwordEncoder.encode(usersDTO.getPass());
            usersDTO.setPass(encoded);
            usersDTO.setRole("USER");
            Users users = modelMapper.map(usersDTO, Users.class);
            Users savedUser = userRepository.save(users);

            // 유저 테이블 생성 동시에 AI로 생성 되는 pk값 가지고 skill 테이블에 값입력하기
            String[] languageNames = usersDTO.getLanguagename();

            if(languageNames !=null && languageNames.length !=0){
                List<Skill> skills = new ArrayList<>();

                for(String languagename : languageNames){
                    SkillDTO skillDTO = new SkillDTO();
                    skillDTO.setUserno(savedUser.getUserno());
                    skillDTO.setLanguagename(languagename);

                    Skill skill = modelMapper.map(skillDTO, Skill.class);
                    skills.add(skill);
                }

                skillRepository.saveAll(skills);
            }
            return ResponseEntity.ok().body(savedUser);
        }
    }

    /**로그인*/
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

    /**회원가입에 모든 언어 가져오기*/
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

