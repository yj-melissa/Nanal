package com.dbd.nanal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int userIdx;

    @Column
    private String id;

    @Column
    private String token;

    private TokenEntity(String id, String token) {
        id = id;
        this.token = token;
    }

    public static TokenEntity createToken(String id, String token) {
        return new TokenEntity(id, token);
    }

    public void changeToken(String token) {
        this.token = token;
    }


}
