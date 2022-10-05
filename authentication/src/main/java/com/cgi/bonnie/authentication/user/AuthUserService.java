package com.cgi.hexagon.authentication.user;

import com.cgi.hexagon.businessrules.user.AuthUserStorage;
import com.cgi.hexagon.businessrules.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUserService implements AuthUserStorage {

    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
