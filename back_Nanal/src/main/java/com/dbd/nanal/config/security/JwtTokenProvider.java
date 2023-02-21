package com.dbd.nanal.config.security;

import com.dbd.nanal.config.oauth.ApplicationOAuth2User;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Optional;
import javax.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtTokenRepository jwtTokenRepository;
    private final UserRepository userRepository;

    @Value("${secretKey}")
    String secretKey;


    // Access Token 기한 = 1일

    private Date accessTokenExpiryDate = Date.from(
        Instant.now().plus(1, ChronoUnit.DAYS)
    );

    // Refresh Token 기한 = 2주
    private Date refreshTokenExpiryDate = Date.from(
        Instant.now().plus(14, ChronoUnit.DAYS)
    );

    // 토큰 생성
    // 일반 로그인
    public JwtTokenDTO createJwtTokens(UserEntity user) {
        String userId = user.getUserId();
        int userIdx = user.getUserIdx();
        String accessToken = createToken(userIdx, accessTokenExpiryDate);
        String refreshToken = createToken(userIdx, refreshTokenExpiryDate);

        JwtTokenDTO jwtTokenDTO= JwtTokenDTO.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .userId(user.getUserId())
            .userIdx(user.getUserIdx())
            .build();

        JwtTokenEntity jwtToken = JwtTokenEntity.builder()
            .userId(userId)
            .userIdx(userIdx)
            .refreshToken(jwtTokenDTO.getRefreshToken())
            .build();

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setMaxAge(14 * 24 * 60 * 60);    // 14일
        refreshTokenCookie.setPath("/");


        if(jwtTokenRepository.existsByUserIdx(userIdx)) {
            // 기존 Refresh 토큰 삭제
            JwtTokenEntity originalToken = jwtTokenRepository.findByUserIdx(userIdx);
            jwtTokenRepository.delete(originalToken);
        }
        jwtTokenRepository.save(jwtToken);

        return jwtTokenDTO;
    }

    // 소셜로그인
    public JwtTokenDTO createJwtTokens(Authentication authentication) {
        ApplicationOAuth2User userPrincipal = (ApplicationOAuth2User) authentication.getPrincipal();
        String userId = userPrincipal.getName();
        int userIdx = userRepository.findByUserId(userId).getUserIdx();
        String accessToken = createToken(userIdx, accessTokenExpiryDate);
        String refreshToken = createToken(userIdx, refreshTokenExpiryDate);

        JwtTokenDTO jwtTokenDTO= JwtTokenDTO.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .userId(userId)
            .userIdx(userIdx)
            .build();

        JwtTokenEntity jwtToken = JwtTokenEntity.builder()
            .userId(userId)
            .userIdx(userIdx)
            .refreshToken(jwtTokenDTO.getRefreshToken())
            .build();

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setMaxAge(14 * 24 * 60 * 60);    // 14일
        refreshTokenCookie.setPath("/");


        if(jwtTokenRepository.existsByUserId(userId)) {
            // 기존 Refresh 토큰 삭제
            JwtTokenEntity originalToken = jwtTokenRepository.findByUserId(userId);
            jwtTokenRepository.delete(originalToken);
        }
        jwtTokenRepository.save(jwtToken);

        return jwtTokenDTO;

    }

//    // Access Token 재발급
    public String updateAccessToken(String token){

        int isValidate = isValidateToken(token);
        Optional<JwtTokenEntity> refreshToken = jwtTokenRepository.findByRefreshToken(token);

        //refresh 토큰 만료 전이고, 저장된 토큰과 일치하면 새 새로운 access 토큰을 생성
        if (isValidate == 0 && refreshToken.isPresent()) {
            return createToken(refreshToken.get().getUserIdx(), accessTokenExpiryDate);
        } else {
            // 토큰 유효하지 않은 경우 재로그인 필요
            return null;
        }
    }

    // Token 발급
    public String createToken(int userIdx, Date expiryDate){
        log.debug("[createToken] 토큰 생성 시작");
        UserEntity user = userRepository.findByUserIdx(userIdx);
        Claims claims = Jwts.claims()
            .setSubject(user.getUserId());
        claims.put("roles", user.getRoles());
        claims.put("userIdx", userIdx);

        String token = Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .setClaims(claims)
            .setIssuer("nanal")
            .setIssuedAt(new Date())
            .setExpiration(expiryDate)
            .compact();

        log.debug("[createToken] 토큰 생성 완료");
        return token;
    }

    // JWT에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserEntity userInfo = userRepository.findByUserId(this.getUserId(token));

        return new UsernamePasswordAuthenticationToken(userInfo, "", userInfo.getAuthorities());
    }

    // 회원 정보 추출
    public String getUserId(String token) {
        log.debug("getUserId - 토큰 기반 회원 구별 정보 추출");

        String info = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();

        log.debug("getUserId - 토큰 기반 회원 구별 정보 추출 완료, info : {}", info);

        return info;
    }

    // 토큰의 유효성 + 만료일자 확인
    public int isValidateToken (String token){
        try {
            Jws<Claims> claims = Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {       // 토큰 만료
            return 1;
        } catch (Exception e) {     // 유효하지 않은 토큰
            return 2;
        }
        return 0;
    }

    public void deleteRefreshToken(int userIdx) {
        jwtTokenRepository.delete(jwtTokenRepository.findByUserIdx(userIdx));
    }

    public void deleteRefreshToken(String userId) {
        jwtTokenRepository.delete(jwtTokenRepository.findByUserId(userId));
    }
}
