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
public class UserDTO {

    private int userIdx;

    private String userName;

    @Email
    private String email;

    private String userId;

    private String userPassword;

    private String nickname;

    private String jwtToken;

    private String img;

    private String Introduction;

    private Boolean isPrivate;

}
