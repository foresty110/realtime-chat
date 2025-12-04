package org.example.stomp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
//사용자 식별 클래스!
public class JwtTokenProvider {

    //1)서명용 키
    private final Key key;
    //2)액세스 토큰 만료 시간
    private final long accessTokenValidity;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity}") long accessTokenValidity) {

        // Jwt에 맞게 변환해주는것!
        // 문자열을 진짜 열쇠로 만드는 것!
        // 내가 가진 secret 값을 가져와서 32글자 이상의 key 바꾸는 작업!
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenValidity = accessTokenValidity;
    }

    // AccessToken생성
    public String generateAccessToken(Long userId) {

        //1) 현재 발급한 시간을 저장
        Date now = new Date();
        //2) 토큰 만료시간을 계산해서 저장
        Date expiry = new Date(now.getTime() + accessTokenValidity);

        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // 토큰발급핵심!userId
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256) //위조됬는지 안됬는지 확인
                .compact();
    }

    //accessToken 검증
    public boolean isTokenValid(String token) {

        try {
            //유효성 검사하고 JWT해독하는 실제 메서드
            parse(token);
        } catch (Exception e) {
            //토큰 검증 하는 사이에 예외가 발생할 수있다.
            // 만료된 토큰,위조 됬을 수도 있다.
            // 어떤 오류라도 터지면 false반환
            return false;
        }
        return true;
    }

    // 파싱하는 메서드
    // Jwt 읽을 준비한다.
    /*
    ExpiredJwtException → 만료됨
    SignatureException → 서명 위조됨
    MalformedJwtException → 구조가 이상함
    UnsupportedJwtException → 지원 안 함
    IllegalArgumentException → null, 빈 문자열
     */
    private Claims parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token) // 실제 토큰 전체검증 지점!
                .getBody();
    }

    //    userId 꺼낸다.
    public Long getUserId(String token) {
        return Long.parseLong(parse(token).getSubject());
    }

}
