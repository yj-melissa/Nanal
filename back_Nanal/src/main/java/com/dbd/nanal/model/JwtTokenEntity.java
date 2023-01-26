package com.dbd.nanal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class JwtTokenEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private int refreshTokenId;

    @Column(name = "user_id")
    private String userId;


    @Column(name = "refresh_token")
    private String refreshToken;

    private JwtTokenEntity(String userId, Integer refreshTokenId, String refreshToken) {
        this.userId = userId;
        this.refreshTokenId = refreshTokenId;
        this.refreshToken = refreshToken;
    }

    @Builder
    public static JwtTokenEntity createToken(String userId, Integer refreshTokenId, String refreshToken) {
        return new JwtTokenEntity(userId, refreshTokenId, refreshToken);
    }


    public void changeToken(String token) {

        this.refreshToken = refreshToken;
    }


}
