package com.dbd.nanal.service;

import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.model.UserProfileEntity;
import com.dbd.nanal.repository.UserProfileRepository;
import com.dbd.nanal.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired private final UserRepository userRepository;
    @Autowired private final UserProfileRepository userProfileRepository;

    public int join(UserEntity user) {
        checkDuplicate(user);
        userRepository.save(user);

        UserProfileEntity userProfile = new UserProfileEntity();
        userProfile.setUser(user);
        userProfileRepository.save(userProfile);

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
