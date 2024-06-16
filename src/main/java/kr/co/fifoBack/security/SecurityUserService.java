package kr.co.fifoBack.security;


import kr.co.fifoBack.entity.Users;
import kr.co.fifoBack.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service @Slf4j @AllArgsConstructor
public class SecurityUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Users> result = userRepository.findByEmail(email);
        UserDetails userDetails = null;

        if(!result.isEmpty()){
            // 해당하는 사용자가 존재하면 인증 객체 생성
            userDetails = MyUserDetails.builder().users(result.get()).build();
        }
        
        // Security ContextHolder 저장
        return userDetails;
    }
}
