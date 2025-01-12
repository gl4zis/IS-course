package ru.itmo.is.security;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.itmo.is.entity.User;

import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Component
@Scope(SCOPE_REQUEST)
@Data
public class SecurityContext {
    private String username;
    private User.Role role;

    public void setContext(String username, User.Role role) {
        this.username = username;
        this.role = role;
    }

    public void setUnauthorized() {
        this.username = null;
        this.role = null;
    }

    public boolean isAuthenticated() {
        return username != null && role != null;
    }
}
