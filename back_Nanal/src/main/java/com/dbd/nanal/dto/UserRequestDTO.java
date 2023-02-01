package com.dbd.nanal.dto;

import javax.validation.constraints.Email;
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

    private String userId;

    @Email
    private String email;

//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*_=-|+`(){}[]:;\"'<>,.?/])[A-Za-z\\d~!@#$%^&*_=-|+`(){}[]:;\"'<>,.?/]{8,16}$", message = "비밀번호는 영문 대소문자, 숫자, 특수문자 각 1자 이상 포함 8~16자로 작성해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*_=-|+`(){}:;\"'<>,.?/])[A-Za-z\\d~!@#$%^&*_=-|+`(){}:;\"'<>,.?/]{8,16}$", message = "비밀번호는 영문 대소문자, 숫자, 특수문자 각 1자 이상 포함 8~16자로 작성해주세요.")
    private String password;

    @Pattern(regexp = "^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]{2,8}$", message = "닉네임은 특수문자를 포함하지 않은 2~8자리로 작성해주세요.") // 영어, 한국어 길이 체크 필요`
    private String nickname;

    private String img;

    private String introduction;

    private Boolean isPrivate;

}
