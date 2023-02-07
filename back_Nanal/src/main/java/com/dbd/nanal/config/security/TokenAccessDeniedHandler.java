package com.dbd.nanal.config.security;

import com.dbd.nanal.config.common.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TokenAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(200);

        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("statusCode", 500);
        HashMap<String, Object> data = new HashMap<>();
        data.put("responseMessage", ResponseMessage.USER_UNAUTHROIZED);
        responseDTO.put("data", data);

        new ObjectMapper().writeValue(response.getWriter(), responseDTO);

    }
}
