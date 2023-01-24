package com.dbd.nanal.controller;

import com.dbd.nanal.config.security.JWTTokenProvider;
import com.dbd.nanal.dto.UserDTO;
import com.dbd.nanal.dto.UserResponseDTO;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.model.UserProfileEntity;
import com.dbd.nanal.service.UserService;

import java.time.LocalDateTime;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private JWTTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid UserDTO userDTO) {
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
        }   catch (RuntimeException e) {

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

        if(user != null) {
            final UserDTO responseUserDTO = UserDTO.builder()
                .userIdx(user.getUserIdx())
                .userId(user.getUserId())
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
