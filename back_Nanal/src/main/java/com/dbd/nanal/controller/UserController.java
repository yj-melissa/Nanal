package com.dbd.nanal.controller;

import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.config.security.JwtTokenProvider;
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
    public ResponseEntity<?> signUp(@RequestBody @Valid UserRequestDTO userRequestDTO) {
            // 정보가 들어오지 않았을 때
            if (userRequestDTO
                == null || userRequestDTO.getPassword() == null || userRequestDTO.getUserId() == null | userRequestDTO.getEmail() == null) {
                throw new NullPointerException(ResponseMessage.EMPTY);
            }

            UserEntity user = UserEntity.builder()
                .userId(userRequestDTO.getUserId())
                .name(userRequestDTO.getName())
                .email(userRequestDTO.getEmail())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .creationDate(LocalDateTime.now())
                .lastAccessDate(LocalDateTime.now())
                .build();


            UserProfileEntity userProfile = UserProfileEntity.builder()
                .user(user)
                .nickname(userRequestDTO.getNickname())
                .img(userRequestDTO.getImg())
                .introduction(userRequestDTO.getIntroduction())
                .isPrivate(userRequestDTO.getIsPrivate())
                .build();

            UserEntity newUser = userService.join(user, userProfile);


            HashMap<String, Object> responseDTO = new HashMap<>();
            responseDTO.put("ResponseMessage", ResponseMessage.CREATED_USER);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);
        }


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        UserEntity user = userService.getByCredentials(
            userRequestDTO.getUserId(),
            userRequestDTO.getPassword(),
            passwordEncoder);

        if(user != null) {
            String token = jwtTokenProvider.createAccessToken(user);

            String userIdx = Integer.toString(user.getUserIdx());
            HashMap<String, String> User = new HashMap<>();
            User.put("userIdx", userIdx);
            User.put("userId", user.getUserId());
            User.put("accessToken", token);

            HashMap<String, Object> responseDTO = new HashMap<>();
            responseDTO.put("ResponseMessage", ResponseMessage.LOGIN_SUCCESS);
            responseDTO.put("User", User);
            return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);

        } else {
            Boolean isUserExist = userService.checkUserId(userRequestDTO.getUserId());

            if (isUserExist) {
                HashMap<String, Object> responseDTO = new HashMap<>();
                responseDTO.put("ResponseMessage", ResponseMessage.LOGIN_FAIL);
                return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
            } else {
                HashMap<String, Object> responseDTO = new HashMap<>();
                responseDTO.put("ResponseMessage", ResponseMessage.NOT_FOUND_USER);
                return new ResponseEntity<>(DefaultRes.res(500, responseDTO), HttpStatus.OK);
            }
        }
    }
}
