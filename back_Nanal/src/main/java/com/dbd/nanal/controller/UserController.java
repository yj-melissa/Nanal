package com.dbd.nanal.controller;

import com.dbd.nanal.config.common.DefaultRes;
import com.dbd.nanal.config.common.ResponseMessage;
import com.dbd.nanal.config.security.JwtTokenProvider;
import com.dbd.nanal.dto.UserDTO;
import com.dbd.nanal.dto.UserResponseDTO;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.model.UserProfileEntity;
import com.dbd.nanal.service.UserService;

import java.time.LocalDateTime;
import java.util.HashMap;
import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO) {
        System.out.println("signUp : " + userDTO.getUserId() + userDTO.getUserPassword());
        try {
            if (userDTO == null || userDTO.getUserPassword() == null) {
                throw new RuntimeException("비밀번호 값이 유효하지 않습니다");
            }

            UserEntity user = UserEntity.builder()
                    .userId(userDTO.getUserId())
                    .userName(userDTO.getUserName())
                    .email(userDTO.getEmail())
                    .userPassword(passwordEncoder.encode(userDTO.getUserPassword()))
                    .creationDate(LocalDateTime.now())
                    .lastAccessDate(LocalDateTime.now())
                    .build();


            UserProfileEntity userProfile = UserProfileEntity.builder()
                    .user(user)
                    .nickname(userDTO.getNickname())
                    .img(userDTO.getImg())
                    .introduction(userDTO.getIntroduction())
                    .isPrivate(userDTO.getIsPrivate())
                    .build();

            UserEntity newUser = userService.join(user, userProfile);

            UserDTO responseUserDTO = UserDTO.builder()
                    .userIdx(newUser.getUserIdx())
                    .userId(newUser.getUserId())
                    .build();


            return ResponseEntity.ok().body(responseUserDTO);
        } catch (RuntimeException e) {

            UserResponseDTO responseDTO = UserResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid UserDTO userDTO) {
        UserEntity user = userService.getByCredentials(
                userDTO.getUserId(),
                userDTO.getUserPassword(),
                passwordEncoder);

        if (user != null) {

            String token = jwtTokenProvider.create(user);

            final UserDTO responseUserDTO = UserDTO.builder()
                    .userIdx(user.getUserIdx())
                    .userId(user.getUserId())
                    .jwtToken(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);

        } else {
            String errorMsg;
            Boolean isUserExist = userService.findUserId(userDTO.getUserId());

            if (isUserExist) {
                errorMsg = "비밀번호가 일치하지 않습니다";
            } else {
                errorMsg = "존재하지 않는 아이디입니다";
            }

            UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                    .error(errorMsg)
                    .build();

            return ResponseEntity
                    .badRequest()
                    .body(userResponseDTO);
        }
    }


}
