package com.dbd.nanal.dto;

import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private int userIdx;

    private String name;

    @Email
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
