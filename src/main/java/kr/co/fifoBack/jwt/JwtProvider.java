package kr.co.fifoBack.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.co.fifoBack.entity.Users;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j @Getter @Component
public class JwtProvider {

    private String issuer;
    private SecretKey secretKey;

    public JwtProvider(@Value("${jwt.issuer}") String issuer,
                       @Value("${jwt.secret}") String secretKey){
        this.issuer = issuer;
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    public String createToken(Users users, int days){

        // 발급일, 만료일 생성
        Date issuedDate = new Date();
        Date expireDate = new Date(issuedDate.getTime() + Duration.ofDays(days).toMillis());
        //Date expireDate = new Date();
        //expireDate.setHours(expireDate.getHours() + 1);

        // 클레임 생성
        Claims claims = Jwts.claims();
        claims.put("userEmail", users.getEmail());
        claims.put("role", users.getRole());

        // 토큰 생성
        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(issuer)
                .setIssuedAt(issuedDate)
                .setExpiration(expireDate)
                .addClaims(claims)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public Claims getClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Authentication getAuthentication(String token){

        // 클레임에서 사용자, 권한 가져오기
        Claims claims = getClaims(token);
        int userNo = (Integer) claims.get("userNo");
        String email  = (String) claims.get("userEmail");
        String role = (String) claims.get("role");

        // 권한목록 생성
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));

        // 사용자 인증객체 생성
        Users principal = Users.builder()
                .userno(userNo)
                .email(email)
                .role(role)
                .build();

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token){

        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            return true;

        }catch (SecurityException | MalformedJwtException e){
            // 잘못된 JWT 서명일 경우
            log.info("MalformedJwtException..." + e.getMessage());
            throw new JwtMyException(JwtMyException.JWT_ERROR.MALFORM);

        }catch (ExpiredJwtException e){
            // 만료된 JWT 경우
            log.info("ExpiredJwtException..." + e.getMessage());
            throw new JwtMyException(JwtMyException.JWT_ERROR.EXPIRED);

        }catch (UnsupportedJwtException e){
            // 지원되지 않은 JWT 경우
            log.info("UnsupportedJwtException..." + e.getMessage());
            throw new JwtMyException(JwtMyException.JWT_ERROR.BADTYPE);

        }catch (IllegalArgumentException e){
            // 잘못된 JWT 경우
            log.info("IllegalArgumentException..." + e.getMessage());
            throw new JwtMyException(JwtMyException.JWT_ERROR.BADSIGN);
        }
    }
}