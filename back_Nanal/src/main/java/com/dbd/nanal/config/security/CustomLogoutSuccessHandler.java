package com.dbd.nanal.config.security;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.dbd.nanal.config.common.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    public final JwtTokenProvider jwtTokenProvider;

    public CustomLogoutSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        String accessToken = parseBearerToken(request);
        String userId = jwtTokenProvider.getUserId(accessToken);

        jwtTokenProvider.deleteRefreshToken(userId);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        response.setStatus(200);

        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("statusCode", 200);
        responseDTO.put("responseMessage", ResponseMessage.LOGOUT_SUCCESS);

        new ObjectMapper().writeValue(response.getWriter(), responseDTO);
        response.getWriter().flush();

//        return new ResponseEntity<>(DefaultRes.res(200, responseDTO), HttpStatus.OK);

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
