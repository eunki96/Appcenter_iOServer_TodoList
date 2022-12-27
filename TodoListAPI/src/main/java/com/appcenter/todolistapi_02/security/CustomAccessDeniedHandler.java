package com.appcenter.todolistapi_02.security;

import lombok.extern.java.Log;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;

//@XSlf4j
@Component
@Log
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServerException{
        log.info("[handle] 접근이 막혔을 경우 경로 리다이렉트");
        response.sendRedirect("/sign-api/exception");
    }
}
