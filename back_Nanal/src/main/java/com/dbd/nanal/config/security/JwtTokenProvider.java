package com.dbd.nanal.config.security;

import com.dbd.nanal.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Service
public class JwtTokenProvider {

    private String secretKey = "dbdNanalSecretKeySpaceTheFinalFrontierTheseAreTheVoyeagesOfTheStarshipEnterpriseItsContinuingMissionToExploreStrangeNewWorlds";

    // 토큰 기한 1일로 설정
    private final Date expiryDate = Date.from(
        Instant.now()
            .plus(1, ChronoUnit.DAYS));

    public String create(UserEntity userEntity) {
        System.out.println("token create");

        String token = Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .setSubject(userEntity.getUserId())
            .setIssuer("nanal")
            .setIssuedAt(new Date())
            .setExpiration(expiryDate)
            .compact();

        return token;
    }

    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();

        return claims.getSubject();
    }

}
