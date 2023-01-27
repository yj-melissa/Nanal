package com.dbd.nanal.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        try {
            // 요청에서 토큰 가져오기
            String token = parseBearerToken(request);
            // 토큰 검사 : JWT이므로 인가 서버에 요청하지 않고 검증 가능하다
            if (token != null && !token.equalsIgnoreCase("null")) {
                // userId 가져오기. 위조된 경우 예외처리됨
                String userId = jwtTokenProvider.getUserId(token);
//                int userIdx = jwtTokenProvider.getUserIdx(token);
                // 인증 완료 : SecurityContextHolder에 등록해야 인증된 사용자라고 생각함
                AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userId,                         // 인증된 사용자의 정보
//                    userIdx,
                    AuthorityUtils.NO_AUTHORITIES);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception e) {
            System.out.println("user authentication 세팅 실패" + e);
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
}
