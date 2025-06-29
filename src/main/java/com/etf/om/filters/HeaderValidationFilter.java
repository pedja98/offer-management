package com.etf.om.filters;

import com.etf.om.enums.UserType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class HeaderValidationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String usernameHeader = request.getHeader("X-Username");
        String userTypeHeader = request.getHeader("X-User-Type");

        if (usernameHeader == null
                || usernameHeader.isEmpty()
                || userTypeHeader == null
                || userTypeHeader.isEmpty()
                || Arrays.stream(UserType.values())
                .noneMatch(type -> type.name().equals(userTypeHeader))) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid header parameters");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
