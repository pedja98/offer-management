package com.etf.om.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SetCurrentUserFilter extends OncePerRequestFilter {

    private static final ThreadLocal<String> currentUsernameThreadLocal = new ThreadLocal<>();

    public static String getCurrentUsername() {
        return currentUsernameThreadLocal.get();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String usernameHeader = request.getHeader("X-Username");
        if (usernameHeader != null && !usernameHeader.isBlank()) {
            currentUsernameThreadLocal.set(usernameHeader);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            currentUsernameThreadLocal.remove();
        }
    }
}
