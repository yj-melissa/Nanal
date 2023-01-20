package com.dbd.nanal.controller;

import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.model.UserProfileEntity;
import com.dbd.nanal.repository.UserProfileRepository;
import com.dbd.nanal.repository.UserRepository;
import com.dbd.nanal.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/signup")
    public String signUp(@Valid UserForm form) {

        UserEntity user = new UserEntity();
        user.setUserName(form.getUserName());
        user.setEmail(form.getEmail());
        user.setUserId(form.getUserId());
        user.setUserPassword(form.getPassword());

        int userIdx = userService.join(user);

//        UserProfileEntity userProfile = new UserProfileEntity(form.getNickname(), form.getIntroduction(), form.getImg(), form.getIsPrivate());

//        userProfile.setUser(user);
//        userProfile.save(userProfile);


        UserProfileEntity userProfile = userProfileRepository.findById((long)userIdx);
        userProfile.setNickname(form.getNickname());

        userProfileRepository.save(userProfile);

        return null;
    }

}
