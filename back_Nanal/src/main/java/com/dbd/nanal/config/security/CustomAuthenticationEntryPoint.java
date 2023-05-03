package com.dbd.nanal.config.security;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.dbd.nanal.config.common.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
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

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("statusCode", 200);
        HashMap<String, Object> data = new HashMap<>();
        data.put("responseMessage", ResponseMessage.TOKEN_NOT_VALID);
        responseDTO.put("data", data);

        new ObjectMapper().writeValue(response.getWriter(), responseDTO);

        log.info("CustomAuthenticationEntryPoint");

    }
}
