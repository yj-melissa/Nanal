package com.dbd.nanal.config.oauth;

import com.dbd.nanal.config.security.JwtTokenProvider;
import com.dbd.nanal.config.security.JwtTokenDTO;
import com.dbd.nanal.config.security.JwtTokenRepository;
import com.dbd.nanal.repository.UserRepository;
import java.io.IOException;
import javax.servlet.ServletException;
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
        JwtTokenDTO token = tokenProvider.createJwtTokens(authentication);
        System.out.println("AccessToken : "+token.getAccessToken());
        System.out.println("RefreshToken : "+token.getRefreshToken());
        response.getWriter().write(token.getAccessToken());
        response.getWriter().write(token.getRefreshToken());
    }
}
