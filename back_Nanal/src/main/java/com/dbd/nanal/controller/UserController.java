package com.dbd.nanal.controller;

import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.model.UserProfileEntity;
import com.dbd.nanal.repository.UserProfileRepository;
import com.dbd.nanal.repository.UserRepository;
import com.dbd.nanal.service.UserService;
import java.time.LocalDateTime;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public String signUp(@Valid UserForm form) {

        UserEntity user = new UserEntity();
        user.setUserName(form.getUserName());
        user.setLoginType("normal");
        user.setEmail(form.getEmail());
        user.setUserId(form.getUserId());
        user.setUserPassword(passwordEncoder.encode(form.getPassword()));
        user.setCreationDate(LocalDateTime.now());
        user.setLastAccessDate(LocalDateTime.now());

        UserProfileEntity userProfile = new UserProfileEntity();
        userProfile.setNickname(form.getNickname());
        userProfile.setIntroduction(form.getIntroduction());
        userProfile.setImg(form.getImg());
        userProfile.setIsPrivate(form.getIsPrivate());

        userService.join(user, userProfile);

        return null;
    }

}
