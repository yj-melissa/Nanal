package com.dbd.nanal.config.oauth;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.dbd.nanal.config.common.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class OAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {
//        super.onAuthenticationFailure(request, response, exception);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        HashMap<String, Object> responseDTO = new HashMap<>();
        responseDTO.put("statusCode", 200);
        HashMap<String, Object> data = new HashMap<>();
        data.put("responseMessage", ResponseMessage.LOGIN_FAIL);
        responseDTO.put("data", data);

        new ObjectMapper().writeValue(response.getWriter(), responseDTO);
    }
}
