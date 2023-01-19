package com.dbd.nanal.service;

import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public int join(UserEntity user) {
        checkDuplicate(user);
        userRepository.save(user);
        return user.getUserIdx();
    }

    private void checkDuplicate(UserEntity user) {
        UserEntity checkUserName = userRepository.findByUserName(user.getUserName());
        if (checkUserName != null) {
            throw new IllegalStateException("사용할 수 없는 아이디");
        }
        UserEntity checkEmail = userRepository.findByEmail(user.getEmail());
        if (checkEmail != null) {
            throw new IllegalStateException("사용할 수 없는 이메일");
        }
    }







}

