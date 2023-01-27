package com.dbd.nanal.config.security;

import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.dto.JwtTokenDTO;
import com.dbd.nanal.model.JwtTokenEntity;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.repository.JwtTokenRepository;
import com.dbd.nanal.service.CustomUserDetailService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class JwtTokenProvider {

    @Autowired private JwtTokenRepository jwtTokenRepository;

    CustomUserDetailService customUserDetailService;

    String secretKey = "daybydayNanalInhighseasorinlowseasImgonnabeyourfriendImgonnabeyourfriendInhighseasorinlowseasIllbebyyoursideIllbebyyourside";


    // Access Token 기한 = 1일
    private final Date accessTokenExpiryDate = Date.from(
        Instant.now().plus(1, ChronoUnit.DAYS)
    );

    // Refresh Token 기한 = 2주
    private final Date refreshTokenExpiryDate = Date.from(
        Instant.now().plus(14, ChronoUnit.DAYS)
    );

    // 토큰 생성
    public JwtTokenDTO createJwtTokens(UserEntity user) {
        String accessToken = createToken(user, accessTokenExpiryDate);
        String refreshToken = createToken(user, refreshTokenExpiryDate);
        String userId = user.getUserId();
        int userIdx = user.getUserIdx();

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

        if(jwtTokenRepository.existsByUserIdx(userIdx)) {
            // 기존 Refresh 토큰 삭제
            JwtTokenEntity originalToken = jwtTokenRepository.findByUserIdx(userIdx);
            jwtTokenRepository.delete(originalToken);
        }
        jwtTokenRepository.save(jwtToken);

        return jwtTokenDTO;
    }

    // Refresh Token 검증
    public String validateRefreshToken(JwtTokenEntity refreshToken, UserEntity user){

        String token = refreshToken.getRefreshToken();
        boolean isValidate = isValidateToken(token);

        //refresh 토큰 만료 전이라면 새로운 access 토큰을 생성
        if (isValidate) {
            return createToken(user, accessTokenExpiryDate);
        } else {
            // 토큰 만료된 경우 재 로그인 필요
            throw new JwtException(ResponseMessage.NOT_VALID_TOKEN);
        }
    }

    // Token 발급
    public String createToken(UserEntity user, Date expiryDate){

        Claims claims = Jwts.claims()
            .setSubject(user.getUserId())
            .setIssuer("nanal")
            .setIssuedAt(new Date())
            .setExpiration(expiryDate);

        claims.put("userIdx", user.getUserIdx());

        String token = Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .setClaims(claims)
            .compact();

        log.info("token : ", token);

        return token;
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {

        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getUserId(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 회원 정보 추출
    public String getUserId(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();

        return claims.getSubject();
    }

    // Request의 Header에서 token 값을 가져옴. "Authorization" : "token값"
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean isValidateToken (String token) {
        try {
            Jws<Claims> claims = Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
