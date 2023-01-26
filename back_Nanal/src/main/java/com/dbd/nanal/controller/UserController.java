package com.dbd.nanal.controller;

import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.config.security.JwtTokenProvider;
import com.dbd.nanal.dto.JwtTokenDTO;
import com.dbd.nanal.dto.UserFormDTO;
import com.dbd.nanal.dto.UserRequestDTO;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.model.UserProfileEntity;
import com.dbd.nanal.service.UserService;

import java.time.LocalDateTime;
import java.util.HashMap;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired  private final UserService userService;

    @Autowired private JwtTokenProvider jwtTokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserFormDTO userformDTO) {
            // 정보가 들어오지 않았을 때
            if (userformDTO
                == null || userformDTO.getPassword() == null || userformDTO.getUserId() == null | userformDTO.getEmail() == null) {
                throw new NullPointerException(ResponseMessage.EMPTY);
            }

            UserEntity user = UserEntity.builder()
                .userId(userformDTO.getUserId())
                .name(userformDTO.getName())
                .email(userformDTO.getEmail())
                .password(passwordEncoder.encode(userformDTO.getPassword()))
                .role("ROLE_USER")
                .creationDate(LocalDateTime.now())
                .lastAccessDate(LocalDateTime.now())
                .build();


            UserProfileEntity userProfile = UserProfileEntity.builder()
                .user(user)
                .nickname(userformDTO.getNickname())
                .img(userformDTO.getImg())
                .introduction(userformDTO.getIntroduction())
                .isPrivate(userformDTO.getIsPrivate())
                .build();

            UserEntity createdUser = userService.join(user, userProfile);
            
//          JWT 토큰 발행
            JwtTokenDTO jwtTokenDTO = jwtTokenProvider.createJwtTokens(createdUser);

            String userIdx = Integer.toString(createdUser.getUserIdx());
            HashMap<String, String> UserInfo = new HashMap<>();
            UserInfo.put("userIdx", userIdx);
            UserInfo.put("userId", createdUser.getUserId());
            UserInfo.put("accessToken", jwtTokenDTO.getAccessToken());
            UserInfo.put("refreshToken", jwtTokenDTO.getRefreshToken());
            

            HashMap<String, Object> responseDTO = new HashMap<>();
            responseDTO.put("ResponseMessage", ResponseMessage.CREATED_USER);
            responseDTO.put("USER", UserInfo);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        UserEntity user = userService.getByCredentials(
            userRequestDTO.getUserId(),
            userRequestDTO.getPassword(),
            passwordEncoder);

        if(user != null) {
            // 로그인 성공
            JwtTokenDTO jwtTokenDTO = jwtTokenProvider.createJwtTokens(user);

            String userIdx = Integer.toString(user.getUserIdx());
            HashMap<String, String> User = new HashMap<>();
            User.put("userIdx", userIdx);
            User.put("userId", user.getUserId());
            User.put("accessToken", jwtTokenDTO.getAccessToken());
            User.put("refreshToken", jwtTokenDTO.getRefreshToken());

            HashMap<String, Object> responseDTO = new HashMap<>();
            responseDTO.put("ResponseMessage", ResponseMessage.LOGIN_SUCCESS);
            responseDTO.put("User", User);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);

        } else {
            // 로그인 실패
            Boolean isUserExist = userService.checkUserId(userRequestDTO.getUserId());

            if (isUserExist) {
                // 비밀번호 미일치
                HashMap<String, Object> responseDTO = new HashMap<>();
                responseDTO.put("ResponseMessage", ResponseMessage.LOGIN_FAIL);
                return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
            } else {
                // 해당 아이디 없음
                HashMap<String, Object> responseDTO = new HashMap<>();
                responseDTO.put("ResponseMessage", ResponseMessage.NOT_FOUND_USER);
                return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
            }
        }
    }
}
