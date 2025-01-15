package ru.itmo.is.web;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.itmo.is.entity.user.User;
import ru.itmo.is.security.RolesAllowed;
import ru.itmo.is.security.Anonymous;
import ru.itmo.is.security.JwtManager;
import ru.itmo.is.security.SecurityContext;
import ru.itmo.is.utils.AnnotationUtils;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private static final String AUTH_PREFIX = "Bearer ";

    private final JwtManager jwtManager;
    private final SecurityContext securityContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith(AUTH_PREFIX)) {
            String token = authHeader.substring(AUTH_PREFIX.length());

            try {
                String username = jwtManager.getLogin(token);
                User.Role role = jwtManager.getRole(token);

                securityContext.setContext(username, role);
            } catch (Exception e) {
                securityContext.setUnauthorized();
            }
        } else {
            securityContext.setUnauthorized();
        }
        return allowed(handler, securityContext.getRole());
    }

    private boolean allowed(Object handler, @Nullable User.Role authenticatedRole) {
        if (handler instanceof HandlerMethod handlerMethod) {
            Anonymous anonymous = AnnotationUtils.getEndpointAnnotation(handlerMethod, Anonymous.class);
            if (anonymous != null && authenticatedRole == null) {
                return true;
            }

            RolesAllowed rolesAllowed = AnnotationUtils.getEndpointAnnotation(handlerMethod, RolesAllowed.class);
            if (rolesAllowed == null) {
                return true;
            }
            return Arrays.asList(rolesAllowed.value()).contains(authenticatedRole);
        }
        return true;
    }

}
