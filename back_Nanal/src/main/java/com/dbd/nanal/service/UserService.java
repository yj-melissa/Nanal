package com.dbd.nanal.service;

import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.model.UserProfileEntity;
import com.dbd.nanal.repository.UserProfileRepository;
import com.dbd.nanal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired private final UserRepository userRepository;
    @Autowired private final UserProfileRepository userProfileRepository;

    public UserEntity join(final UserEntity userEntity, final UserProfileEntity userProfileEntity) {
        if(userEntity == null || userProfileEntity == null || userEntity.getUserId() == null || userEntity.getEmail() == null || userProfileEntity.getNickname() == null) {
            throw new RuntimeException("필수항목이 입력되었는지 확인하세요");
        }

        final String userId = userEntity.getUserId();
        if(userRepository.existsByUserId(userId)) {
            throw new RuntimeException("이미 사용중인 아이디입니다");
        }

        final String email = userEntity.getEmail();
        if(userRepository.existsByEmail(email)) {
            throw new RuntimeException("이미 사용중인 이메일입니다");
        }

        final String nickname = userProfileEntity.getNickname();
        if(userProfileRepository.existsByNickname(nickname)) {
            throw new RuntimeException("이미 사용중인 닉네임입니다");
        }

        UserEntity newUser = userRepository.save(userEntity);
        userProfileEntity.setUser(newUser);
        UserProfileEntity newProfile = userProfileRepository.save(userProfileEntity);

        return newUser;
    }


    public UserEntity getByCredentials(final String userId, final String userPassword, final PasswordEncoder passwordEncoder) {

        final UserEntity user = userRepository.findByUserId(userId);
        if(user != null && passwordEncoder.matches(userPassword, user.getUserPassword())) {
            return user;
        }
        return null;
    }

    public Boolean findUserId(String userId) {
        Boolean result = userRepository.existsByUserId(userId);
        return result;
    }

}
