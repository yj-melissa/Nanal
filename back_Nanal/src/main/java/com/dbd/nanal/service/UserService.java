package com.dbd.nanal.service;

import com.dbd.nanal.dto.UserRequestDTO;
import com.dbd.nanal.model.PaintingEntity;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.model.UserProfileEntity;
import com.dbd.nanal.repository.PaintingRepository;
import com.dbd.nanal.repository.UserProfileRepository;
import com.dbd.nanal.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PaintingRepository paintingRepository;

    // 회원 가입
    public UserEntity join(final UserEntity userEntity, final UserProfileEntity userProfileEntity) {

        final String userId = userEntity.getUserId();
        checkUserId(userId);

        final String email = userEntity.getEmail();
        checkEmail(email);

        final String nickname = userProfileEntity.getNickname();
        checkNickname(nickname);

        UserEntity newUser = userRepository.save(userEntity);
        userProfileEntity.setUser(newUser);
        userProfileRepository.save(userProfileEntity);

        return newUser;
    }

    // 로그인
    public UserEntity getByCredentials(final String userId, final String userPassword, final PasswordEncoder passwordEncoder) {

        final UserEntity user = userRepository.findByUserId(userId);
        if(user != null && passwordEncoder.matches(userPassword, user.getPassword())) {
            user.setLastAccessDate(LocalDateTime.now());
            return user;
        }
        return null;
    }

    // 비밀번호 확인
    public Boolean getByUserIdxAndPassword(final Integer userIdx, final String password, final PasswordEncoder passwordEncoder) {
        final UserEntity user = userRepository.findByUserIdx(userIdx);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    public List<UserEntity> getUserList() {
        return userRepository.findAll();
    }

    // 유저 프로필 조회
    public HashMap<String, Object> getProfileByUserIdx(final int userIdx) {
        return profileDTO(userProfileRepository.findByProfileIdx(userIdx));
    }


    // 회원 정보 수정
    public HashMap<String, Object> updateProfile(final int userIdx, final UserRequestDTO userRequest) {
        UserProfileEntity profile = userProfileRepository.findByProfileIdx(userIdx);

        profile.setNickname(userRequest.getNickname());
//        profile.setImg(userRequest.getImg());
        profile.setIntroduction(userRequest.getIntroduction());
//        profile.setIsPrivate(userForm.getIsPrivate());

        profile = userProfileRepository.save(profile);

        return profileDTO(profile);
    }
    // 비밀번호 수정

    public void updatePassword(int userIdx, String newPassword) {

        UserEntity user = userRepository.findByUserIdx(userIdx);
        user.setPassword(newPassword);
        userRepository.save(user);

    }
    // 회원 탈퇴

    public void deleteByUserIdx(int userIdx) {
        userRepository.deleteById(userIdx);
    }

    // 비밀번호 확인용
    public Boolean isUserExist(String userId) {
        return userRepository.existsByUserId(userId);
    }

    // 중복 체크

//    public void checkUserId(String userId) {
//        Boolean result = userRepository.existsByUserId(userId);
//        if (result) {
//            throw new DuplicateKeyException(userId);
//        }
//    }
    public Boolean checkUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }

//    public void checkNickname(String nickname) {
//        Boolean result = userProfileRepository.existsByNickname(nickname);
//        if (result) {
//            throw new DuplicateKeyException(nickname);
//        }
//    }
    public Boolean checkNickname(String nickname) {
        return userProfileRepository.existsByNickname(nickname);
    }

//    public void checkEmail(String email) {
//        Boolean result = userRepository.existsByEmail(email);
//        if (result) {
//            throw new DuplicateKeyException(email);
//        }
//    }
    public Boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    @Transactional
    public void updateUserImg(int userIdx, int pictureIdx, String newImgUrl) {
        System.out.println("useridx : "+userIdx+ " newImgUrl : "+newImgUrl);
        UserProfileEntity userProfileEntity = userProfileRepository.getReferenceById(userIdx);
        System.out.println("userIdx : "+userIdx+" 변경 전 img idx : "+userProfileEntity.getImg()+" 변경 후 img idx : "+newImgUrl);
        userProfileEntity.setImg(newImgUrl);

        PaintingEntity painting = paintingRepository.getReferenceById(pictureIdx);
        userProfileEntity.setPainting(painting);
    }

    public HashMap<String, Object> profileDTO(UserProfileEntity userProfile) {
        LocalDate createDate = userProfile.getUser().getCreationDate().toLocalDate();
        long days = (LocalDate.now().until(createDate, ChronoUnit.DAYS) * -1) + 1;

        HashMap<String, Object> profile = new HashMap<>();
        profile.put("img", userProfile.getImg());
        profile.put("nickname", userProfile.getNickname());
        profile.put("introduction", userProfile.getIntroduction());
        profile.put("days", days);

        return profile;
    }
}

