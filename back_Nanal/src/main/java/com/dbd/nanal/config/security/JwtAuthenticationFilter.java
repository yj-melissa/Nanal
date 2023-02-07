package com.dbd.nanal.config.security;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.dbd.nanal.config.common.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 인증에서 제외할 url
    private static final List<String> EXCLUDE_URL =
        Collections.unmodifiableList(
            Arrays.asList(
                "/",
                "/user/signup",
                "/user/login",
                "/user/oauth2",
                "/oauth2/**",
                "/user/refresh",    // accessToken 재발급
                "/user/redirectTest",
                "/user/check/**",
                "/user/validate/**",
                // Swagger 관련 URL
                "/swagger-ui/",
                "/v2/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/webjars/**",
                "/swagger/**",
                "/sign-api/exception/**"
            ));

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = parseBearerToken(request);
        int isValidate = jwtTokenProvider.isValidateToken(token);

        if (token != null && isValidate == 0) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

//                filterChain.doFilter(request, response);
        } else if (isValidate == 1) {           // 토큰이 만료된 경우
            logger.info("token 만료");

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");

            HashMap<String, Object> responseDTO = new HashMap<>();
            responseDTO.put("statusCode", 500);
            HashMap<String, Object> data = new HashMap<>();
            data.put("responseMessage", ResponseMessage.TOKEN_EXPIRED);
            responseDTO.put("data", data);

            new ObjectMapper().writeValue(response.getWriter(), responseDTO);
        }
        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        // Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴함
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Filter에서 제외할 URL 설정
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
    }

}
