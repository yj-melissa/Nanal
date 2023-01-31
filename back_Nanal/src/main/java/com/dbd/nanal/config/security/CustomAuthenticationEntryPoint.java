package com.dbd.nanal.config.security;

import com.dbd.nanal.config.common.ResponseMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
//        final Map<String, Object> body = new HashMap<>();
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        body.put("responseMessage", ResponseMessage.UNAUTHROIZED_USER);
//        body.put("status", HttpStatus.OK);
        response.sendRedirect("/nanal/user/login");
    }
}
