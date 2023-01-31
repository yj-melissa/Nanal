package com.dbd.nanal.config.security;

import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.dto.JwtTokenDTO;
import com.dbd.nanal.model.JwtTokenEntity;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.repository.JwtTokenRepository;
import com.dbd.nanal.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

//    @Autowired
    private final JwtTokenRepository jwtTokenRepository;
    private final UserRepository userRepository;

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

        if(jwtTokenRepository.existsByUserIdx(userIdx)) {
            // 기존 Refresh 토큰 삭제
            JwtTokenEntity originalToken = jwtTokenRepository.findByUserIdx(userIdx);
            jwtTokenRepository.delete(originalToken);
        }
        jwtTokenRepository.save(jwtToken);

        return jwtTokenDTO;
    }

//    // Access Token 재발급
//    public String reissueAccessToken(int userIdx, String token){
//
//        boolean isValidate = isValidateToken(token);
//        Optional<JwtTokenEntity> refreshToken = jwtTokenRepository.findByRefreshToken(token);
//
//        //refresh 토큰 만료 전이고, 저장된 토큰과 일치하면 새 새로운 access 토큰을 생성
//        if (isValidate && refreshToken.isPresent()) {
//            return createToken(userIdx, accessTokenExpiryDate);
//        } else {
//            // 토큰 유효하지 않은 경우 재 로그인 필요
//            throw new JwtException(ResponseMessage.NOT_VALID_TOKEN);
//        }
//    }

    // Token 발급
    public String createToken(int userIdx, Date expiryDate){
        log.debug("[createToken] 토큰 생성 시작");
        UserEntity user = userRepository.findByUserIdx(userIdx);
        Claims claims = Jwts.claims()
            .setSubject(user.getUserId());
        claims.put("roles", user.getRoles());

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
        log.debug("getAuthentication - 토큰 인증 정보 조회 시작");

        UserEntity userInfo = userRepository.findByUserId(this.getUserId(token));

        log.debug("getAuthentication - 토큰 인증 정보 조회 완료, userInfo userName : {}", userInfo.getUsername());

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

//    // Request의 Header에서 token 값을 가져옴. "Authorization" : "token값"
//    public String resolveToken(HttpServletRequest request) {
//        return request.getHeader("Authorization");
//    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean isValidateToken (String token) {
        log.debug("isValidateToken - 토큰 유효 체크 시작");
        try {
            Jws<Claims> claims = Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.debug("isValidateToken - 토큰 유효체크 예외 발생");
            return false;
        }
    }


    public void deleteRefreshToken(int userIdx) {
        jwtTokenRepository.delete(jwtTokenRepository.findByUserIdx(userIdx));
    }
}
