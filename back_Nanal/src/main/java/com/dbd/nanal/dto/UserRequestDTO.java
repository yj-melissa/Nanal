package com.dbd.nanal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private String name;

    private String email;
    
    private String userId;

    private String password;

    private String nickname;

    private String refreshToken;

    private String accessToken;

    private String img;

    private String introduction;

    private Boolean isPrivate;

}
