package kr.co.fifoBack.security;



import kr.co.fifoBack.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter @Setter @ToString @Builder
public class MyUserDetails implements UserDetails {

    private Users users;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 계정이 갖는 권한 목록
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(users.getRole()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return users.getPass();
    }

    @Override
    public String getUsername() {
        return users.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정 만료 여부(true: 만료안됨, false: 만료됨)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠김 여부(true: 잠김안됨, false: 잠김)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 비밀번호 만료 여부(true:만료안됨, false:만료)
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 계정 활성화 여부(true: 활성화 ,false: 비활성화)
        return users.getLeaveDate() == null;
    }
}
