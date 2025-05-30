package com.welab.backend_user.secret.jwt.props;

import com.welab.backend_user.secret.jwt.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

// JWT(JSON Web Token) 를 생성하는 Spring Boot 컴포넌트
@Component
@RequiredArgsConstructor
public class TokenGenerator {

    // JWT 관련 설정값 (만료 시간, 비밀키)
    private final JwtConfigProperties configProperties;
    // volatile 키워드: 멀티 스레드 환경에서 동기화 문제 방지
    // 컴파일러나 CPU의 명령어 순서 재정렬 방지
    private volatile SecretKey secretKey;

    // [1] SecretKey 초기화 🔥
    // Double-Checked Locking 패턴 사용
    /*
        getSecretKey 메소드 여러 스레드가 동시 호출 가능
        secretKey가 null일 때 여러 스레드가 동시에 초기화를 시도하면, 중복 초기화가 일어나거나 스레드 간 충돌 가능
        ex. A와 B가 동시 호출 -> secretKey == null 판정 -> 동시에 초기화 시도 -> race condition
        ==> secretKey가 두 번 초기화되거나, 예외 발생 가능
        ==> 동기화 블록과 DCL 패턴 사용
    * */
    private SecretKey getSecretKey() {
        if (secretKey == null) {
            // 초기화 필요한 경우, 동기화 블록 진입
            // Monitor Lock을 걸면, 한 번에 하나의 스레드만 진입 가능 -> 다른 스레드는 락을 획득할 때까지 대기 상태(BLOCKED)로 들어감
            // 서로 간섭 없이 임계 영역이 실행되도록 보장
            // 다시 확인 (double check: 여러 스레드가 동기화 블록에 진입했더라도 최초 1개 스레드만 초기화하도록 보장)
            synchronized (this) {
                if (secretKey == null) {
                    secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(configProperties.getSecretKey()));
                }
            }
        }
        return secretKey;
    }

    public TokenDto.AccessToken generateAccessToken(String userId, String deviceType) {
        TokenDto.JwtToken jwtToken = this.generateJwtToken(userId, deviceType, false);
        return new TokenDto.AccessToken(jwtToken);
    }
    // AccessToken + RefreshToken 둘 다 생성해서 묶어서 리턴
    public TokenDto.AccessRefreshToken generateAccessRefreshToken(String userId, String deviceType) {
        TokenDto.JwtToken accessJwtToken = this.generateJwtToken(userId, deviceType, false);
        TokenDto.JwtToken refreshJwtToken = this.generateJwtToken(userId, deviceType, true);
        return new TokenDto.AccessRefreshToken(accessJwtToken, refreshJwtToken);
    }

    public TokenDto.JwtToken generateJwtToken(String userId, String deviceType, boolean refreshToken
    ) {
        int tokenExpiresIn = tokenExpiresIn(refreshToken, deviceType);
        String tokenType = refreshToken ? "refresh" : "access";
        String token = Jwts.builder()
                .issuer("welab")
                .subject(userId)
                .claim("userId", userId)
                .claim("deviceType", deviceType)
                .claim("tokenType", tokenType)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpiresIn * 1000L)).signWith(getSecretKey())
                .header().add("typ", "JWT")
                .and()
                .compact();
        return new TokenDto.JwtToken(token, tokenExpiresIn);
    }

    private int tokenExpiresIn(boolean refreshToken, String deviceType) {
        // AccessToken은 기본 15분(900초)으로 설정
        int expiresIn = 60 * 15;
        // RefreshToken의 경우 deviceType에 따라 만료시간 다르게 설정
        if (refreshToken) {
            if (deviceType != null) {
                if (deviceType.equals("MOBILE")) {
                    expiresIn = configProperties.getMobileExpiresIn();
                } else if (deviceType.equals("TABLET")) {
                    expiresIn = configProperties.getTabletExpiresIn();
                } else { // "WEB" 일 경우 포함
                    expiresIn = configProperties.getExpiresIn();
                }
            } else {
                expiresIn = configProperties.getExpiresIn();
            }
        }
        return expiresIn;
    }

    public String validateJwtToken(String refreshToken) {
        String userId = null;
        final Claims claims = this.verifyAndGetClaims(refreshToken);
        // (1) refreshToken이 위조되었거나 파싱에 실패한 경우 (ex. 서명 불일치, 구조 이상)
        if (claims == null) {
            return null;
        }
        Date expirationDate = claims.getExpiration();
        // (2) 토큰이 만료된 경우
        if (expirationDate == null || expirationDate.before(new Date())) {
            return null;
        }
        userId = claims.get("userId", String.class);
        String tokenType = claims.get("tokenType", String.class);
        // (3) 토큰 타입이 'refresh'가 아닐 경우 (access 토큰일 수도 있음)
        if (!"refresh".equals(tokenType)) {
            return null;
        }
        // (4) 정상적인 refreshToken인 경우 => 해당 유저 ID 반환
        return userId;
    }

    private Claims verifyAndGetClaims(String token) {
        Claims claims;
        try {
            // 서명 검증하고, payload(Claims) 추출
            claims = Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
}

