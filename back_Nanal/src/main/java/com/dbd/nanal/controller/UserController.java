package com.dbd.nanal.controller;

import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.config.security.JwtTokenProvider;
import com.dbd.nanal.config.security.JwtTokenDTO;
import com.dbd.nanal.dto.UserFormDTO;
import com.dbd.nanal.dto.UserRequestDTO;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.model.UserProfileEntity;
import com.dbd.nanal.service.EmailService;
import com.dbd.nanal.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    private final UserService userService;
    private final EmailService emailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @ApiOperation(value = "회원가입", notes =
            "[Front] \n" +
                    "JSON\n" +
                    "{userId(String), password(String), email(String), nickname(String)} \n\n" +
                    "[Back] \n" +
                    "JSON\n" +
                    "{accessToken(String), refreshToken(String)} ")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserFormDTO userformDTO, HttpServletResponse response) {
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
                .roles(Collections.singletonList("ROLE_USER"))
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
        HashMap<String, String> token = createTokens(user);

        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("responseMessage", ResponseMessage.USER_CREATE_SUCCESS);
        responseDTO.put("accessToken", token.get("accessToken"));
        response.setHeader("accessToken", token.get("accessToken"));

//        int cookieExpTime = 14 * 24 * 60 * 60;     // 초단위 : 14일로 설정
//        Cookie refreshTokenCookie = new Cookie("refreshToken", token.get("refreshToken"));
//        refreshTokenCookie.setMaxAge(cookieExpTime);    // 초 단위
//        refreshTokenCookie.setPath("/");     // 모든 경로에서 접근 가능

        Cookie refreshTokenCookie = refreshTokenCookie(token.get("refreshToken"));

        response.addCookie(refreshTokenCookie);

        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "이메일 인증")
    @GetMapping("/validate/{email}")
    public ResponseEntity<?> validateEmail(@PathVariable String email) throws Exception {
        // 이메일 중복 확인
        ResponseEntity<?> isDuplicate = checkEmail(email);
        if (isDuplicate == null) {
            throw new DuplicateKeyException(email);
        }

        String code = emailService.sendSimpleMessage(email);

        try {
            HashMap<String, Object> responseDTO = new HashMap<>();
            if (code == null) {
                responseDTO.put("responseMessage", ResponseMessage.EMAIL_SEND_FAIL);

                return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
            }

            responseDTO.put("responseMessage", ResponseMessage.EMAIL_SEND_SUCCESS);
            responseDTO.put("code", code);

            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        } catch (Exception e){
            log.info("[이메일 인증] 발송 실패 :"+e);
            HashMap<String, Object> responseDTO = new HashMap<>();
            responseDTO.put("responseMessage", ResponseMessage.EMAIL_SEND_FAIL);
            responseDTO.put("code", code);

            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "로그인", notes =
            "[Front] \n" +
                    "JSON\n" +
                    "{userId(String), password(String)} \n\n" +
                    "[Back] \n" +
                    "JSON\n" +
                    "{accessToken(String), refreshToken(String)} ")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO userRequestDTO, HttpServletResponse response) {
        UserEntity user = userService.getByCredentials(
                userRequestDTO.getUserId(),
                userRequestDTO.getPassword(),
                passwordEncoder);

        if(user != null) {
            // 로그인 성공 -> JWT 발급
            HashMap<String, String> token = createTokens(user);

            HashMap<String, Object> responseDTO = new HashMap<>();
            responseDTO.put("responseMessage", ResponseMessage.LOGIN_SUCCESS);
            responseDTO.put("token", token);
            response.setHeader("accessToken", token.get("accessToken"));

            // 쿠키생성
//            int cookieExpTime = 14 * 24 * 60 * 60;     // 초단위 : 14일로 설정
//            Cookie accessTokenCookie = new Cookie("accessToken", token.get("accessToken"));
//            accessTokenCookie.setMaxAge(cookieExpTime);    // 초 단위
//            accessTokenCookie.setPath("/");     // 모든 경로에서 접근 가능

//            response.addCookie(accessTokenCookie);

//            Cookie refreshTokenCookie = new Cookie("refreshToken", token.get("refreshToken"));
//            refreshTokenCookie.setMaxAge(cookieExpTime);
//            refreshTokenCookie.setPath("/");     // 모든 경로에서 접근 가능

            Cookie refreshTokenCookie = refreshTokenCookie(token.get("refreshToken"));

            response.addCookie(refreshTokenCookie);

            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);

        } else {
            // 로그인 실패
            Boolean isUserExist = userService.isUserExist(userRequestDTO.getUserId());
            HashMap<String, Object> responseDTO = new HashMap<>();

            if (isUserExist) {
                // 비밀번호 미일치
                responseDTO.put("responseMessage", ResponseMessage.LOGIN_FAIL);
            } else {
                // 해당 아이디 없음
                responseDTO.put("responseMessage", ResponseMessage.USER_FIND_FAIL);
            }
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }

    public Cookie refreshTokenCookie(String refreshToken) {
        int cookieExpTime = 14 * 24 * 60 * 60;     // 초단위 : 14일로 설정
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setMaxAge(cookieExpTime);
        refreshTokenCookie.setPath("/");     // 모든 경로에서 접근 가능

        return refreshTokenCookie;
    }

    @ApiOperation(value = "로그아웃")
    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserEntity userInfo) {

        jwtTokenProvider.deleteRefreshToken(userInfo.getUserIdx());

        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("responseMessage", ResponseMessage.LOGOUT_SUCCESS);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }


    @ApiOperation(value = "Access Token 재발급")
    @GetMapping("/refresh")
    public ResponseEntity<?> updateAccessToken(HttpServletResponse response, @CookieValue(name = "refreshToken", required = false) String refreshToken) throws IOException {
        String newToken = jwtTokenProvider.updateAccessToken(refreshToken);

        HashMap<String, Object> responseDTO = new HashMap<>();

        if (newToken == null) {
            response.sendRedirect("/nanal/user/login");
            responseDTO.put("responseMessage", ResponseMessage.TOKEN_NOT_VALID);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        responseDTO.put("responseMessage", ResponseMessage.SUCCESS);
        responseDTO.put("accessToken", newToken);
        response.setHeader("accessToken", newToken);

        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "내 프로필 조회", notes =
            "[Front] \n" +
            "[Back] \n" +
                "JSON\n" +
                "{img(String), nickname(String), introduction(String), days(long)} \n\n")
    @GetMapping("/profile")
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal UserEntity userInfo) {
        HashMap<String, Object> profile = userService.getByUserIdx(userInfo.getUserIdx());

        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("responseMessage", ResponseMessage.USER_FIND_SUCCESS);
        responseDTO.put("profile", profile);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    // 테스트용 api (Access Token 헤더로 받은 경우)
//    @GetMapping("/test")
//    public ResponseEntity<?> test(@ApiParam(value = "userIdx") @AuthenticationPrincipal UserEntity userInfo) {
//
//        HashMap<String, Object> responseDTO = new HashMap<>();
//        responseDTO.put("responseMessage", ResponseMessage.SUCCESS);
//        responseDTO.put("user", userInfo.getUserId());
//
//        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
//    }

    // 테스트용 api (Access Token 쿠키로 받은 경우)
//    @GetMapping("/test")
//    public ResponseEntity<?> test(@ApiParam(value = "userIdx") @CookieValue(name = "accessToken", required = false) String accessToken) {
//
//        HashMap<String, Object> responseDTO = new HashMap<>();
//        String userId = jwtTokenProvider.getUserId(accessToken);
//        responseDTO.put("userId", userId);
//        responseDTO.put("responseMessage", ResponseMessage.SUCCESS);
//
//        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
//    }

    @ApiOperation(value = "회원 정보 수정", notes =
            "[Front] \n" +
                "{img(String), nickname(String), introduction(String)} \n\n" +
            "[Back] \n" +
                "OK(200) \n\n")
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@ApiParam(value = "userIdx") @AuthenticationPrincipal UserEntity userInfo, @RequestBody @Valid UserRequestDTO userRequest) {

        if (userRequest == null) {
            throw new NullPointerException(ResponseMessage.EMPTY);
        }

        userService.updateProfile(userInfo.getUserIdx(), userRequest);
        jwtTokenProvider.deleteRefreshToken(userInfo.getUserIdx());

        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("responseMessage", ResponseMessage.USER_UPDATE_SUCCESS);

        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "회원 탈퇴", notes =
            "[Front] \n" +
                "{userIdx(int)} \n\n" +
                "[Back] \n" +
                "OK(200) \n\n")
    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserEntity userInfo) {
        userService.deleteByUserIdx(userInfo.getUserIdx());
        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("responseMessage", ResponseMessage.USER_DELETE_SUCCESS);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "다른 회원 프로필 조회", notes =
            "[Front] \n" +
                "{userIdx(int)} \n\n" +
            "[Back] \n" +
                "JSON\n" +
                "{img(String), nickname(String), introduction(String), days(long)} \n\n")
    @GetMapping("/profile/{userIdx}")
    public ResponseEntity<?> getUserProfile(@PathVariable int userIdx) {
        HashMap<String, Object> profile = userService.getByUserIdx(userIdx);
        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("responseMessage", ResponseMessage.SUCCESS);
        responseDTO.put("profile", profile);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "비밀번호 확인", notes =
            "[Front] \n" +
                "JSON\n" +
                "{password(String)} \n\n" +
            "[Back] \n" +
                "OK(200), RUNTIME(500)  \n\n")
    @PostMapping("/password")
    public ResponseEntity<?> checkPassword(@AuthenticationPrincipal UserEntity userInfo, @RequestBody @Valid UserRequestDTO userRequestDTO) {
        String password = userRequestDTO.getPassword();

        Boolean isCorrect = userService.getByUserIdxAndPassword(
                userInfo.getUserIdx(),
                password,
                passwordEncoder);

        HashMap<String, Object> responseDTO = new HashMap<>();

        if(isCorrect) {
            responseDTO.put("responseMessage", ResponseMessage.LOGIN_SUCCESS);

            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        } else {
            responseDTO.put("responseMessage", ResponseMessage.LOGIN_FAIL);
            return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "비밀번호 수정", notes =
            "[Front] \n" +
                "JSON\n" +
                "{password(String)} \n\n" +
            "[Back] \n" +
                "OK(200) \n\n")
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@ApiParam(value = "userIdx") @AuthenticationPrincipal UserEntity userInfo, @RequestBody UserRequestDTO userRequestDTO) {

        if (userRequestDTO.getPassword() == null) {
            throw new NullPointerException(ResponseMessage.EMPTY);
        }

        String newPassword = passwordEncoder.encode(userRequestDTO.getPassword());

        userService.updatePassword(userInfo.getUserIdx(), newPassword);

        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("responseMessage", ResponseMessage.PASSWORD_UPDATE_SUCEESS);

        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "유저 목록 반환")
    @GetMapping("/userlist")
    public ResponseEntity<?> getUserList() {
        List<UserEntity> userList = userService.getUserList();
        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("responseMessage", ResponseMessage.USER_LIST_FIND_SUCCESS);
        responseDTO.put("userList", userList);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    // 중복확인
    @ApiOperation(value = "아이디 중복 확인", notes =
            "[Front] \n" +
                "{userId(String)} \n\n" +
            "[Back] \n" +
                "OK(200), DUPLICATE KEY(500)")
    @GetMapping("/check/id/{userId}")
    public ResponseEntity<?> checkUserId(@PathVariable String userId) {
        userService.checkUserId(userId);
        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("responseMessage", ResponseMessage.USUABLE_KEY);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "닉네임 중복 확인", notes =
            "[Front] \n" +
                "{nickname(String)} \n\n" +
            "[Back] \n" +
                "OK(200), DUPLICATE KEY(500)")
    @GetMapping("/check/nickname/{nickname}")
    public ResponseEntity<?> checkNickname(@PathVariable String nickname) {
        userService.checkNickname(nickname);
        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("responseMessage", ResponseMessage.USUABLE_KEY);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "이메일 중복 확인", notes =
            "[Front] \n" +
                "{email(String)} \n\n" +
            "[Back] \n" +
                "OK(200), DUPLICATE KEY(500)")
    @GetMapping("/check/Email/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable String email) {
        userService.checkEmail(email);
        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("responseMessage", ResponseMessage.USUABLE_KEY);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    public HashMap<String, String> createTokens(UserEntity user) {
        JwtTokenDTO jwtTokenDTO = jwtTokenProvider.createJwtTokens(user);

        HashMap<String, String> token = new HashMap<>();
        token.put("accessToken", jwtTokenDTO.getAccessToken());
        token.put("refreshToken", jwtTokenDTO.getRefreshToken());

        return token;
    }
}
