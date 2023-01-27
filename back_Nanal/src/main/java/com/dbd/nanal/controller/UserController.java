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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired  private final UserService userService;

    @Autowired private JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    
    // 회원가입
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
            log.info("createdUser : "+createdUser.getUserIdx());
            log.info("createdUser : "+createdUser.getUserId());

//          JWT 토큰 발행
            JwtTokenDTO jwtTokenDTO = jwtTokenProvider.createJwtTokens(createdUser);

            log.info("jwtTokenDTO"+jwtTokenDTO);
            String userIdx = Integer.toString(createdUser.getUserIdx());
            HashMap<String, String> Token = new HashMap<>();
            Token.put("accessToken", jwtTokenDTO.getAccessToken());
            Token.put("refreshToken", jwtTokenDTO.getRefreshToken());

            HashMap<String, Object> responseDTO = new HashMap<>();
            responseDTO.put("ResponseMessage", ResponseMessage.CREATED_USER);
            responseDTO.put("Token", Token);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        UserEntity user = userService.getByCredentials(
            userRequestDTO.getUserId(),
            userRequestDTO.getPassword(),
            passwordEncoder);

        if(user != null) {
            // 로그인 성공
            JwtTokenDTO jwtTokenDTO = jwtTokenProvider.createJwtTokens(user);

            HashMap<String, String> Token = new HashMap<>();
            Token.put("accessToken", jwtTokenDTO.getAccessToken());
            Token.put("refreshToken", jwtTokenDTO.getRefreshToken());

            HashMap<String, Object> responseDTO = new HashMap<>();
            responseDTO.put("ResponseMessage", ResponseMessage.LOGIN_SUCCESS);
            responseDTO.put("Token", Token);
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

    @GetMapping("/test")
    public String test() {
        return "test 성공";
    }


}
