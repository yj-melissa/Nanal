package com.dbd.nanal.controller;


import static org.junit.Assert.assertEquals;

import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.repository.UserRepository;
import com.dbd.nanal.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

public class UserControllerTest {

        @Autowired UserService userService;
        @Autowired UserRepository userRepository;

    @Test
    @Rollback(value = false)
    public void 회원가입() {
        //given
        UserEntity user = new UserEntity();
        user.setUserName("dd1");
        user.setUserId("test");
        user.setUserPassword("asdf");
        user.setEmail("dd@test.com");

        //when
        int savedId = userService.join(user);
//        System.out.println(userRepository.findOne(savedId));

        //then
//        assertEquals(user, userRepository.findOne(savedId));

    }
}