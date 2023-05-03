package com.dbd.nanal.config.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class JwtTokenEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id", columnDefinition = "INT UNSIGNED")
    private int refreshTokenId;

    @Column(name = "user_idx")
    private int userIdx;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "refresh_token")
    private String refreshToken;


}
