package com.dbd.nanal.config.security;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.dbd.nanal.config.common.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;
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

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {

        String[] noCheckUri = {
            "/user/login",
            "/user/signup",
            "/user/check",
            "/login/oauth2"
        };

        String path = request.getServletPath();

        if (Arrays.stream(noCheckUri).anyMatch(uri -> path.startsWith(uri) )) {

            filterChain.doFilter(request, response);
        } else {
            String token = parseBearerToken(request);
            int isValidate = jwtTokenProvider.isValidateToken(token);


            if (isValidate == 1) {           // 토큰이 만료된 경우
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");

                HashMap<String, Object> responseDTO = new HashMap<>();
                responseDTO.put("statusCode", 200);
                HashMap<String, Object> data = new HashMap<>();
                data.put("responseMessage", ResponseMessage.TOKEN_EXPIRED);
                responseDTO.put("data", data);

                new ObjectMapper().writeValue(response.getWriter(), responseDTO);
                response.getWriter().flush();
            } else {
                if (token != null && isValidate == 0) {
                    Authentication authentication = jwtTokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);}

                filterChain.doFilter(request, response);
            }
        }
    }

    private String parseBearerToken(HttpServletRequest request) {
        // Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴함
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
