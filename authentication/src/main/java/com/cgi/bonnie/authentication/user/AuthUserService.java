package com.cgi.bonnie.authentication.user;

import com.cgi.bonnie.authentication.auth.ApplicationUser;
import com.cgi.bonnie.authentication.security.oauth2.CustomOAuth2User;
import com.cgi.bonnie.businessrules.user.AuthUserStorage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService implements AuthUserStorage {

    public String getCurrentUsername() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomOAuth2User) {
            CustomOAuth2User user = (CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return user.getName();
        } else {
            ApplicationUser user = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return user.getUsername();
        }
    }

    public String getCurrentUserEmail() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomOAuth2User) {
            CustomOAuth2User user = (CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return user.getEmail();
        } else {
            ApplicationUser user = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return user.getEmail();
        }
    }

}
