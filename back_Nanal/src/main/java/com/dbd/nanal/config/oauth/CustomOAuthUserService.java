package com.dbd.nanal.config.oauth;

import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.model.UserProfileEntity;
import com.dbd.nanal.repository.UserProfileRepository;
import com.dbd.nanal.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuthUserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User oAuth2User = super.loadUser(userRequest);  // 기존 loadUser 호출. user-info-uri 이용해 사용자 정보 가져옴
        final String authProvider = userRequest.getClientRegistration().getClientName();
        OAuth2AccessToken oAuth2AccessToken = userRequest.getAccessToken();

        Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
        long id = (long) oAuth2User.getAttributes().get("id");
        String userId = Long.toString(id);
        final String email = (String) kakaoAccount.get("email");
        int socialCode = 1;         // 카카오 socialCode = 1

        UserEntity user;
        UserProfileEntity profile;

        // 기존 유저라면 프로필 업데이트
        if(userRepository.existsByEmail(email)) {
            user = userRepository.findByEmail(email);
            profile = userProfileRepository.findByProfileIdx(userRepository.findByEmail(email).getUserIdx());
            profile.setNickname((String) kakaoProfile.get("nickname"));
            profile.setImg((String) kakaoProfile.get("thumbnail_image_url"));
            userProfileRepository.save(profile);
        } else {
            user = UserEntity.builder()
                .userId(userId)
                .email(email)
                .socialCode(socialCode)          // 0 -> 기본 로그인, 1 -> 카카오
                .roles(Collections.singletonList("ROLE_USER"))
                .creationDate(LocalDateTime.now())
                .lastAccessDate(LocalDateTime.now())
                .build();

            profile = UserProfileEntity.builder()
                .user(user)
                .nickname((String) kakaoProfile.get("nickname"))
                .img((String) kakaoProfile.get("thumbnail_image_url"))
                .isPrivate(false)
                .build();

            user.setUserProfile(profile);

            user = userRepository.save(user);
        }

        return new ApplicationOAuth2User(user.getUserId(), oAuth2User.getAttributes(), oAuth2AccessToken);
    }
}
