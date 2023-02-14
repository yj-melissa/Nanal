package com.dbd.nanal.controller;

import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.config.security.JwtTokenDTO;
import com.dbd.nanal.config.security.JwtTokenProvider;
import com.dbd.nanal.dto.PaintingRequestDTO;
import com.dbd.nanal.dto.UserFormDTO;
import com.dbd.nanal.dto.UserRequestDTO;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.model.UserProfileEntity;
import com.dbd.nanal.service.EmailService;
import com.dbd.nanal.service.FileService;
import com.dbd.nanal.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Api(tags = {"User 관련 API"})
@CrossOrigin
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final FileService fileService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @ApiOperation(value = "회원가입", notes =
            "회원가입을 진행합니다 \n" +
                    "[Front] \n" +
                    "{userId(String), password(String), email(String), nickname(String)} \n\n" +
                    "[Back] \n" +
                    "{accessToken(String), refreshToken(String)} \n" +
                    "{accessToken(Header), refreshToken(Cookie)} \n")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserFormDTO userformDTO, HttpServletResponse response) {
        HashMap<String, Object> responseDTO = new HashMap<>();
        if (userformDTO == null || userformDTO.getPassword() == null || userformDTO.getUserId() == null | userformDTO.getEmail() == null || userformDTO.getNickname() == null) {
            // 정보가 들어오지 않았을 때
            responseDTO.put("responseMessage", ResponseMessage.USER_CREATE_FAIL);
        } else if (userService.checkUserId(userformDTO.getUserId()) || userService.checkEmail(userformDTO.getEmail()) || userService.checkNickname(userformDTO.getNickname())) {
            // 중복값이 있을 때
            responseDTO.put("responseMessage", ResponseMessage.DUPLICATED_KEY);
        } else {
            UserEntity user = UserEntity.builder()
                .userId(userformDTO.getUserId())
                .name(userformDTO.getName())
                .email(userformDTO.getEmail())
                .password(passwordEncoder.encode(userformDTO.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .creationDate(LocalDateTime.now())
                .lastAccessDate(LocalDateTime.now())
                .build();

            PaintingRequestDTO paintingRequestDTO = new PaintingRequestDTO();
            paintingRequestDTO.init();
            fileService.paintingSave(paintingRequestDTO);

            UserProfileEntity userProfile = UserProfileEntity.builder()
                .user(user)
                .nickname(userformDTO.getNickname())
                .introduction(userformDTO.getIntroduction())
                .isPrivate(userformDTO.getIsPrivate())
                .painting(paintingRequestDTO.toEntity())
                .img(paintingRequestDTO.getImgUrl())
                .build();

            UserEntity createdUser = userService.join(user, userProfile);
            log.debug("createdUser : " + createdUser);

//          JWT 토큰 발행
            HashMap<String, String> token = createTokens(user);

            responseDTO.put("responseMessage", ResponseMessage.USER_CREATE_SUCCESS);
            responseDTO.put("accessToken", token.get("accessToken"));
            response.setHeader("accessToken", token.get("accessToken"));

            Cookie refreshTokenCookie = refreshTokenCookie(token.get("refreshToken"));

            response.addCookie(refreshTokenCookie);
        }

        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "이메일 인증", notes =
            "회원 가입 시 이메일을 인증합니다. \n" +
                    "[Front] \n" +
                    "{email(String)} \n\n" +
                    "[Back] \n" +
                    "{code(String), refreshToken(String)} \n")
    @GetMapping("/validate/{email}")
    public ResponseEntity<?> validateEmail(@PathVariable String email) throws Exception {
        HashMap<String, Object> responseDTO = new HashMap<>();

        // 이메일 중복 확인
        Boolean isDuplicate = userService.checkEmail(email);

        if (isDuplicate) {
            responseDTO.put("responseMessage", ResponseMessage.DUPLICATED_KEY);
        } else {
            String code = emailService.sendSimpleMessage(email);

            responseDTO.put("responseMessage", ResponseMessage.EMAIL_SEND_SUCCESS);
            responseDTO.put("code", code);
        }

        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "로그인", notes =
            "로그인을 진행합니다. \n" +
                    "[Front] \n" +
                    "{userId(String), password(String)} \n\n" +
                    "[Back] \n" +
                    "{accessToken(String), refreshToken(String)} \n" +
                    "{accessToken(Header), refreshToken(Cookie)}")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO userRequestDTO, HttpServletResponse response) {
        UserEntity user = userService.getByCredentials(
                userRequestDTO.getUserId(),
                userRequestDTO.getPassword(),
                passwordEncoder);

        HashMap<String, Object> responseDTO = new HashMap<>();

        if (user != null) {
            // 로그인 성공 -> JWT 발급
            HashMap<String, String> token = createTokens(user);

            responseDTO.put("responseMessage", ResponseMessage.LOGIN_SUCCESS);
            responseDTO.put("token", token);
            response.setHeader("accessToken", token.get("accessToken"));

            Cookie refreshTokenCookie = refreshTokenCookie(token.get("refreshToken"));

            response.addCookie(refreshTokenCookie);
        } else {
            // 로그인 실패
            Boolean isUserExist = userService.isUserExist(userRequestDTO.getUserId());

            if (isUserExist) {
                // 비밀번호 미일치
                responseDTO.put("responseMessage", ResponseMessage.LOGIN_FAIL);
            } else {
                // 해당 아이디 없음
                responseDTO.put("responseMessage", ResponseMessage.USER_FIND_FAIL);
            }
        }
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "로그아웃", notes =
            "로그아웃을 진행합니다. \n" +
                    "[Front] \n" +
                    "{} \n" +
                    "[Back] \n" +
                    "{responseMessage(String)} \n")
    @PostMapping("/logout")
    public void logout() {
    }

    @ApiOperation(value = "Access Token 재발급", notes =
            "Access Token을 재발급합니다.\n\n" +
                    "[Front] \n" +
                    "[Back] \n" +
                    "{accessToken(Header), refreshToken(Cookie)} \n")
    @GetMapping("/refresh")
    public ResponseEntity<?> updateAccessToken(HttpServletResponse response, @CookieValue(name = "refreshToken", required = false) String refreshToken) throws IOException {
        String newToken = jwtTokenProvider.updateAccessToken(refreshToken);

        HashMap<String, Object> responseDTO = new HashMap<>();

        if (newToken == null) {
            response.sendRedirect("/nanal/user/login");
            responseDTO.put("responseMessage", ResponseMessage.TOKEN_NOT_VALID);
        } else {
            responseDTO.put("responseMessage", ResponseMessage.SUCCESS);
            responseDTO.put("accessToken", newToken);
            response.setHeader("accessToken", newToken);
        }

        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "내 프로필 조회", notes =
            "내 프로필을 조회합니다.\n" +
                    "[Front] \n" +
                    "{} \n" +
                    "[Back] \n" +
                    "{img(String), nickname(String), introduction(String), days(long)} \n\n")
    @GetMapping("/profile")
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal UserEntity userInfo) {
        final UserProfileEntity userProfile = userInfo.getUserProfile();
        HashMap<String, Object> profile = userService.profileDTO(userProfile);

        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("responseMessage", ResponseMessage.USER_FIND_SUCCESS);
        responseDTO.put("profile", profile);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "다른 회원 프로필 조회", notes =
        "유저 인덱스로 다른 회원의 프로필을 조회합니다 .\n\n" +
            "[Front] \n" +
            "{userIdx(int)} \n\n" +
            "[Back] \n" +
            "{img(String), nickname(String), introduction(String), days(long)} \n\n")
    @GetMapping("/profile/{userIdx}")
    public ResponseEntity<?> getUserProfile(@PathVariable int userIdx) {
        HashMap<String, Object> profile = userService.getProfileByUserIdx(userIdx);
        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("responseMessage", ResponseMessage.SUCCESS);
        responseDTO.put("profile", profile);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "회원 정보 수정", notes =
            "회원 정보를 수정합니다.\n" +
                    "[Front] \n" +
                    "{nickname(String), introduction(String)} \n\n" +
                    "[Back] \n" +
                    "{img(String), nickname(String), days(int), introduction(String)} \n\n")
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserEntity userInfo, @RequestBody @Valid UserRequestDTO userRequest) {
        log.info("updateProfile 실행 : {}", userInfo.getUserProfile().getNickname());

        HashMap<String, Object> responseDTO = new HashMap<>();

        if (userRequest == null) {
            log.info("updateProfile : userRequest == null");
            responseDTO.put("responseMessage", ResponseMessage.USER_UPDATE_FAIL);
        } else if (userInfo.getUserProfile().getNickname().equals(userRequest.getNickname())) {
            // 닉네임 변경한 경우 중복 체크
            if (userService.checkNickname(userRequest.getNickname())) {
                responseDTO.put("responseMessage", ResponseMessage.DUPLICATED_KEY);
            }
        }else{
            HashMap<String, Object> profile = userService.updateProfile(userInfo.getUserIdx(), userRequest);

            responseDTO.put("responseMessage", ResponseMessage.USER_UPDATE_SUCCESS);
            responseDTO.put("profile", profile);
        }

        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "회원 탈퇴", notes =
            "회원 탈퇴를 진행합니다. \n\n" +
                    "[Front] \n" +
                    "{} \n\n" +
                    "[Back] \n" +
                    "{responseMessage(String)} \n\n")
    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserEntity userInfo) {
        log.info("deleteUser 실행");
        int userIdx = userInfo.getUserIdx();
        userService.deleteByUserIdx(userInfo.getUserIdx());
        jwtTokenProvider.deleteRefreshToken(userIdx);
        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("responseMessage", ResponseMessage.USER_DELETE_SUCCESS);
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "비밀번호 확인", notes =
            "[Front] \n" +
                    "{password(String)} \n\n" +
                    "[Back] \n" +
                    "{responseMessage(String)}\n\n")
    @PostMapping("/password")
    public ResponseEntity<?> checkPassword(@AuthenticationPrincipal UserEntity userInfo, @RequestBody @Valid UserRequestDTO userRequestDTO) {
        String password = userRequestDTO.getPassword();

        Boolean isCorrect = userService.getByUserIdxAndPassword(
                userInfo.getUserIdx(),
                password,
                passwordEncoder);

        HashMap<String, Object> responseDTO = new HashMap<>();

        if (isCorrect) {
            responseDTO.put("responseMessage", ResponseMessage.LOGIN_SUCCESS);
        } else {
            responseDTO.put("responseMessage", ResponseMessage.LOGIN_FAIL);
        }
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "비밀번호 수정", notes =
            "비밀번호를 수정합니다. \n" +
                    "[Front] \n" +
                    "{password(String)} \n\n" +
                    "[Back] \n" +
                    "{responseMessage(String)} \n\n")
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@ApiParam(value = "userIdx") @AuthenticationPrincipal UserEntity userInfo, @RequestBody @Valid UserRequestDTO userRequestDTO) {

        HashMap<String, Object> responseDTO = new HashMap<>();
        if (userRequestDTO.getPassword() == null) {
            responseDTO.put("responseMessage", ResponseMessage.EMPTY);
        } else {
            String newPassword = passwordEncoder.encode(userRequestDTO.getPassword());

            userService.updatePassword(userInfo.getUserIdx(), newPassword);

            responseDTO.put("responseMessage", ResponseMessage.PASSWORD_UPDATE_SUCEESS);
        }

        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "유저 목록 반환", notes =
            "유저 목록을 반환합니다. \n" +
                    "[Front] \n" +
                    "{} \n\n" +
                    "[Back] \n" +
                    "{userIdx(int), name(String), creationDate(LocalDateTime), lastAccessDate(LocalDateTime), email(String), userId(String), socialCode(int), roles(List), userProfile} \n\n")
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
                    "{responseMessage(String)}")
    @GetMapping("/check/id/{userId}")
    public ResponseEntity<?> checkUserId(@PathVariable String userId) {
        HashMap<String, Object> responseDTO = new HashMap<>();

        if (userService.checkUserId(userId)) {
            responseDTO.put("responseMessage", ResponseMessage.DUPLICATED_KEY);
        } else {
            responseDTO.put("responseMessage", ResponseMessage.USUABLE_KEY);
        }

        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "닉네임 중복 확인", notes =
            "[Front] \n" +
                    "{nickname(String)} \n\n" +
                    "[Back] \n" +
                    "{responseMessage(String)}")
    @GetMapping("/check/nickname/{nickname}")
    public ResponseEntity<?> checkNickname(@PathVariable String nickname) {
        HashMap<String, Object> responseDTO = new HashMap<>();

        if (userService.checkNickname(nickname)) {
            responseDTO.put("responseMessage", ResponseMessage.DUPLICATED_KEY);
        } else {
            responseDTO.put("responseMessage", ResponseMessage.USUABLE_KEY);
        }

        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "이메일 중복 확인", notes =
            "[Front] \n" +
                    "{email(String)} \n\n" +
                    "[Back] \n" +
                    "{responseMessage(String)}")
    @GetMapping("/check/Email/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable String email) {
        HashMap<String, Object> responseDTO = new HashMap<>();

        if (userService.checkEmail(email)) {
            responseDTO.put("responseMessage", ResponseMessage.DUPLICATED_KEY);
        } else {
            responseDTO.put("responseMessage", ResponseMessage.USUABLE_KEY);
        }
        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "카카오 로그인", notes =
            "카카오 계정으로 로그인을 진행합니다. 첫 로그인이라면 계정을 생성합니다.\n" +
                    "[Front] \n" +
                    "{} \n\n" +
                    "[Back] \n" +
                    "{accessToken(Header), kakaoAccessToken(header), refreshToken(Cookie)}")
    @GetMapping("/oauth2/kakao")
    public void kakaoLogin() {
    }

    public Cookie refreshTokenCookie(String refreshToken) {
        int cookieExpTime = 14 * 24 * 60 * 60;     // 초단위 : 14일로 설정
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setMaxAge(cookieExpTime);
        refreshTokenCookie.setPath("/");     // 모든 경로에서 접근 가능

        return refreshTokenCookie;
    }

    public HashMap<String, String> createTokens(UserEntity user) {
        JwtTokenDTO jwtTokenDTO = jwtTokenProvider.createJwtTokens(user);

        HashMap<String, String> token = new HashMap<>();
        token.put("accessToken", jwtTokenDTO.getAccessToken());
        token.put("refreshToken", jwtTokenDTO.getRefreshToken());

        return token;
    }


}
