package com.cgi.bonnie.authentication.user;

import com.cgi.bonnie.businessrules.user.AuthUserStorage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

@Component
public class AuthUserService implements AuthUserStorage {

    public String getCurrentUsername() {
        DefaultOAuth2User user =(DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getAttribute("name");
    }
}
