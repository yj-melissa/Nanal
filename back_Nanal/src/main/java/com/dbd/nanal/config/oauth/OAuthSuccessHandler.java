package com.dbd.nanal.config.oauth;

import com.dbd.nanal.config.security.JwtTokenProvider;
import com.dbd.nanal.config.security.JwtTokenDTO;
import com.dbd.nanal.config.security.JwtTokenRepository;
import com.dbd.nanal.repository.UserRepository;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenRepository jwtTokenRepository;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        JwtTokenProvider tokenProvider = new JwtTokenProvider(jwtTokenRepository, userRepository);

        JwtTokenDTO jwtTokenDTO = tokenProvider.createJwtTokens(authentication);

        // 토큰

        int expTime = 10;

        Cookie refreshTokenCookie = new Cookie("refreshToken", jwtTokenDTO.getRefreshToken());
        refreshTokenCookie.setMaxAge(expTime * 60);    // 초 단위
        refreshTokenCookie.setPath("/");     // 모든 경로에서 접근 가능

        response.addCookie(refreshTokenCookie);
        response.setStatus(200);
        response.addHeader("accessToken", jwtTokenDTO.getAccessToken());
    }
}
