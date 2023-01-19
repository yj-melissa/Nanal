package com.dbd.nanal.controller;

import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.model.UserProfileEntity;
import com.dbd.nanal.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signUp(@Valid UserForm form, BindingResult result) {

        if (result.hasErrors()) {
            return null;
        }

        UserProfileEntity userProfile = new UserProfileEntity(form.getNickname(), form.getIntroduction(), form.getImg(), form.getIsPrivate());

        UserEntity user = new UserEntity();
        user.setUserName(form.getUserName());
        user.setEmail(form.getEmail());
        user.setUserId(form.getUserId());
        user.setUserPassword(form.getPassword1());
        user.setUserProfile(userProfile);

        userService.join(user);
        return null;

    }

}
