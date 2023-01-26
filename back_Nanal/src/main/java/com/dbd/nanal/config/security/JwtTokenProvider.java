package com.dbd.nanal.config.security;

import com.dbd.nanal.dto.JwtTokenDTO;
import com.dbd.nanal.model.JwtTokenEntity;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Service
public class JwtTokenProvider {

    CustomUserDetailService customUserDetailService;

    private String secretKey = "dbdNanalSecretKeySpaceTheFinalFrontierTheseAreTheVoyeagesOfTheStarshipEnterpriseItsContinuingMissionToExploreStrangeNewWorlds";

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

        String accessToken = Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .setSubject(user.getUserId())
            .setIssuer("nanal")
            .setIssuedAt(new Date())
            .setExpiration(accessTokenExpiryDate)
            .compact();

        String refreshToken = Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .setSubject(user.getUserId())
            .setIssuer("nanal")
            .setIssuedAt(new Date())
            .setExpiration(refreshTokenExpiryDate)
            .compact();

        return JwtTokenDTO.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .userId(user.getUserId())
            .build();
    }
//
//    public String validateRefreshToken(JwtTokenEntity refreshToken, UserEntity user){
//
//        String token = refreshToken.getRefreshToken();
//
//        try {
//            // 검증
//            Jws<Claims> claims = Jwts.parserBuilder()
//                .setSigningKey(secretKey)
//                .build().parseClaimsJws(token);
//
//            //refresh 토큰 만료 전이라면 새로운 access 토큰을 생성
//            if (!claims.getBody().getExpiration().before(new Date())) {
//                return recreationAccessToken(user);
//            }
//        }catch (Exception e) {
//            //refresh 토큰이 만료되었을 경우, 재 로그인 필요
//            return null;
//        }
//        return null;
//    }
//
//    // Access Token 재발급
//    public String recreationAccessToken(UserEntity user){
//
//        String accessToken = Jwts.builder()
//            .signWith(SignatureAlgorithm.HS512, secretKey)
//            .setSubject(user.getUserId())
//            .setIssuer("nanal")
//            .setIssuedAt(new Date())
//            .setExpiration(accessTokenExpiryDate)
//            .compact();
//
//        return accessToken;
//    }

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
//
//    // Request의 Header에서 token 값을 가져옴. "Authorization" : "token값"
//    public String resolveToken(HttpServletRequest request) {
//        return request.getHeader("Authorization");
//    }
//
//    // 토큰의 유효성 + 만료일자 확인
//    public boolean isValidateToken(String token) {
//        try {
//            Jws<Claims> claims = Jwts
//                .parserBuilder()
//                .setSigningKey(secretKey)
//                .build().parseClaimsJws(token);
//            return !claims.getBody().getExpiration().before(new Date());
//        } catch (Exception e) {
//            return false;
//        }
//    }

}
