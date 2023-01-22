package com.dbd.nanal.service;

import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.model.UserProfileEntity;
import com.dbd.nanal.repository.UserProfileRepository;
import com.dbd.nanal.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired private final UserRepository userRepository;
    @Autowired private final UserProfileRepository userProfileRepository;

    public int join(UserEntity user, UserProfileEntity userProfile) {

        checkDuplicate(user);
        checkNickname(userProfile);

        userRepository.save(user);

        userProfile.setUser(user);
        userProfileRepository.save(userProfile);

        return user.getUserIdx();
    }

    private void checkDuplicate(UserEntity user) {
        Optional<UserEntity> checkUserName = userRepository.findByUserId(user.getUserId());
        if (!checkUserName.isEmpty()) {
            throw new IllegalStateException("사용할 수 없는 아이디");
        }
        Optional<UserEntity> checkEmail = userRepository.findByEmail(user.getEmail());
        if (!checkEmail.isEmpty()) {
            throw new IllegalStateException("사용할 수 없는 이메일");
        }
    }

    private void checkNickname(UserProfileEntity userProfile) {
        Optional<UserProfileEntity> checkNickname = userProfileRepository.findByNickname(
            userProfile.getNickname());
        if (!checkNickname.isEmpty()) {
            throw new IllegalStateException("사용할 수 없는 닉네임");
        }
    }

}
