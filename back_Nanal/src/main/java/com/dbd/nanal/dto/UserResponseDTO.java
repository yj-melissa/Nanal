package com.dbd.nanal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseDTO {
//    private String error;
//    private List<T> data;

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
