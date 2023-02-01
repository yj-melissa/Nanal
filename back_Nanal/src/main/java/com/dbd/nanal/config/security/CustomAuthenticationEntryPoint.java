package com.dbd.nanal.config.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
//        final Map<String, Object> body = new HashMap<>();
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        body.put("responseMessage", ResponseMessage.UNAUTHROIZED_USER);
//        body.put("status", HttpStatus.OK);
        log.info("CustomAuthenticationEntryPoint");
        response.sendRedirect("/nanal/user/refresh");
    }
}
