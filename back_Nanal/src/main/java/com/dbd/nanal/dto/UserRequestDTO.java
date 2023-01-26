package com.dbd.nanal.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣]{2,10}$", message = "이름은 한글 2자~10자로 작성해주세요.")
    private String name;

    @Email
    @NotBlank
    private String email;
    
    @Pattern(regexp = "^[a-z0-9]{2,10}$", message = "아이디는 영문 소문자, 숫자를 포함한 2~10자리로 작성해주세요.")
    @NotBlank
    private String userId;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 영문 대소문자, 숫자, 특수문자 각 1자 이상 포함 8~16자로 작성해주세요.")
    private String password;

    @Pattern(regexp = "^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]{2,10}$", message = "닉네임은 특수문자를 포함하지 않은 2~10자리로 작성해주세요.")
    private String nickname;

    private String refreshToken;

    private String accessToken;

    private String img;

    private String introduction;

    private Boolean isPrivate;

}
