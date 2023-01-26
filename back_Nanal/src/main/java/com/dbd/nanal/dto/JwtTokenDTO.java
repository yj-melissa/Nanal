package com.dbd.nanal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenDTO {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private String userId;
    private int userIdx;

}
