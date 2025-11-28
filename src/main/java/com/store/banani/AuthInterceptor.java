package com.store.banani;

import com.store.banani.config.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String path = request.getRequestURI();

        if (path.equals("/account/login") || path.startsWith("/css") || path.startsWith("/static/js")) {
            return true;
        }

        boolean isLogin = CookieUtils.hasCookie(request, "loggedInUser");
        if (!isLogin) {
            response.sendRedirect("/account/login");
            return false;
        }

        return true;
    }
}
