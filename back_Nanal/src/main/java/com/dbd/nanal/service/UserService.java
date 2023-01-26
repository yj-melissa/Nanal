package com.dbd.nanal.service;

import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.model.UserProfileEntity;
import com.dbd.nanal.repository.UserProfileRepository;
import com.dbd.nanal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired private final UserRepository userRepository;
    @Autowired private final UserProfileRepository userProfileRepository;

    public UserEntity join(final UserEntity userEntity, final UserProfileEntity userProfileEntity) {
        if (userEntity == null || userProfileEntity == null || userEntity.getUserId() == null || userEntity.getEmail() == null || userProfileEntity.getNickname() == null) {
            System.out.println("유저"+userEntity);
            throw new NullPointerException("");
        }

        final String userId = userEntity.getUserId();
        if(userRepository.existsByUserId(userId)) {
            throw new DuplicateKeyException(userId);
        }

        final String email = userEntity.getEmail();
        if(userRepository.existsByEmail(email)) {
            throw new DuplicateKeyException(email);
        }

        final String nickname = userProfileEntity.getNickname();
        if(userProfileRepository.existsByNickname(nickname)) {
            throw new DuplicateKeyException(nickname);
        }

        UserEntity newUser = userRepository.save(userEntity);
        userProfileEntity.setUser(newUser);
        userProfileRepository.save(userProfileEntity);

        return newUser;
    }

    public UserEntity getByCredentials(final String userId, final String userPassword, final PasswordEncoder passwordEncoder) {

        final UserEntity user = userRepository.findByUserId(userId);
        if(user != null && passwordEncoder.matches(userPassword, user.getPassword())) {
            return user;
        }
        return null;
    }

    public Boolean checkUserId(String userId) {
        Boolean result = userRepository.existsByUserId(userId);
        return result;
    }

    public UserEntity loadByUserId(String userId) {
      return userRepository.findByUserId(userId);
    };
}

