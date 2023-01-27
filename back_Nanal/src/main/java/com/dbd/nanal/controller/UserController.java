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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"User 관련 API"})
@CrossOrigin
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired  private UserService userService;

    @Autowired private JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    
    // 회원가입
    @ApiOperation(value = "회원가입", notes =
        "[Front] \n" +
            "JSON\n" +
            "{userId(String), password(String), email(String), nickname(String)} \n\n" +
        "[Back] \n" +
            "JSON\n" +
            "{accessToken(String), refreshToken(String)} ")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(
        @RequestBody @Valid UserFormDTO userformDTO) {
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
            log.debug("createdUser : "+createdUser);

//          JWT 토큰 발행
            JwtTokenDTO jwtTokenDTO = jwtTokenProvider.createJwtTokens(createdUser);

            HashMap<String, String> Token = new HashMap<>();
            Token.put("accessToken", jwtTokenDTO.getAccessToken());
            Token.put("refreshToken", jwtTokenDTO.getRefreshToken());

            HashMap<String, Object> responseDTO = new HashMap<>();
            responseDTO.put("ResponseMessage", ResponseMessage.CREATED_USER);
            responseDTO.put("Token", Token);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }


    // 로그인
    @ApiOperation(value = "로그인", notes =
        "[Front] \n" +
            "JSON\n" +
            "{userId(String), password(String)} \n\n" +
        "[Back] \n" +
            "JSON\n" +
            "{accessToken(String), refreshToken(String)} ")
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

    @ApiOperation(value = "아이디 중복 확인", notes =
        "[Front] \n" +
            "{userId(String)} \n\n" +
        "[Back] \n" +
            "OK(200), DUPLICATE KEY(500)")
    @GetMapping("/checkid/{userId}")
    public ResponseEntity<?> checkUserId(@PathVariable String userId) {
        userService.checkUserId(userId);
        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("ResponseMessage", ResponseMessage.SUCCESS);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "닉네임 중복 확인", notes =
        "[Front] \n" +
            "{nickname(String)} \n\n" +
        "[Back] \n" +
            "OK(200), DUPLICATE KEY(500)")
    @GetMapping("/checknickname/{nickname}")
    public ResponseEntity<?> checkNickname(@PathVariable String nickname) {
        userService.checkNickname(nickname);
        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("ResponseMessage", ResponseMessage.SUCCESS);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "이메일 중복 확인", notes =
        "[Front] \n" +
            "{email(String)} \n\n" +
        "[Back] \n" +
            "OK(200), DUPLICATE KEY(500)")
    @GetMapping("/checkemail/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable String email) {
        userService.checkEmail(email);
        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("ResponseMessage", ResponseMessage.SUCCESS);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "회원 정보 조회", notes =
        "[Front] \n" +
            "{userIdx(int)} \n\n" +
        "[Back] \n" +
            "JSON\n" +
            "{img(String), nickname(String), introduction(String)} \n\n")
    @GetMapping("/profile/{userIdx}")
    public ResponseEntity<?> updateProfile(@PathVariable int userIdx) {
        HashMap<String, String> profile = userService.getByUserIdx(userIdx);
        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("ResponseMessage", ResponseMessage.SUCCESS);
        responseDTO.put("Profile", profile);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

//    @ApiOperation(value = "회원 정보 수정")
//    @PutMapping("/profile/{userIdx}")
//    public ResponseEntity<?> updateProfile(@ApiParam(value = "userIdx")
//    @RequestBody UserRequestDTO userDTO) {
//
//    }

    @ApiOperation(value = "회원 탈퇴", notes =
        "[Front] \n" +
            "{userIdx(int)} \n\n" +
        "[Back] \n" +
            "OK(200) \n\n")
    @DeleteMapping("/delete/{userIdx}")
    public ResponseEntity<?> deleteUser(@PathVariable int userIdx) {
        userService.deleteByUserIdx(userIdx);
        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("ResponseMessage", ResponseMessage.SUCCESS);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }



    @GetMapping("/test")
    public String test() {
        return "test 성공";
    }


}
