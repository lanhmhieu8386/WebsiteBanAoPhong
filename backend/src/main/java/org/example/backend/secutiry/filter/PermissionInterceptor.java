package org.example.backend.secutiry.filter;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.backend.secutiry.annotation.RequirePermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
    private static final Logger logger =
            LoggerFactory.getLogger(PermissionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        logger.info("===== PERMISSION INTERCEPTOR =====");
        if (!(handler instanceof HandlerMethod)) {
            logger.info("Handler is not HandlerMethod → skip");
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequirePermission requirePermission =
                handlerMethod.getMethodAnnotation(RequirePermission.class);

        if (requirePermission == null) {
            logger.info("No @RequirePermission annotation → allow");
            return true;
        }
        String requiredPermission = requirePermission.value();
        logger.info("Required permission: {}", requiredPermission);
        List<String> permissions =
                (List<String>) request.getAttribute("permissions");
        logger.info("Permissions from request: {}", permissions);
        if (permissions == null || !permissions.contains(requiredPermission)) {
            logger.warn("Permissions NULL → blocking request");
            logger.warn("Permission NOT FOUND → blocking request");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        logger.info("PERMISSION GRANTED");

        return true;
    }

}
