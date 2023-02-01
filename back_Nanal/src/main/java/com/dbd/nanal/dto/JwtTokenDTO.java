package com.dbd.nanal.dto;

//import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter @Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class JwtTokenDTO {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private String userId;
    private int userIdx;

    public JwtTokenDTO(String grantType, String accessToken, String refreshToken, String userId,
        int userIdx) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.userIdx = userIdx;
    }
}
