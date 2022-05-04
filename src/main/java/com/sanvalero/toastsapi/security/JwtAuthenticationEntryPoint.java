package com.sanvalero.toastsapi.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            org.springframework.security.core.AuthenticationException authException)
            throws IOException, ServletException {

        final String expired = (String) request.getAttribute("expired");
        System.out.println(expired);
        if (request.getRequestURI().startsWith("/mel/")) {
            if (expired != null) {
                response.sendError(HttpServletResponse.SC_OK, expired);
            } else {
                response.sendError(HttpServletResponse.SC_OK, "Invalid Login details");
            }
        } else {
            if (expired != null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, expired);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Login details");
            }
        }
    }
}