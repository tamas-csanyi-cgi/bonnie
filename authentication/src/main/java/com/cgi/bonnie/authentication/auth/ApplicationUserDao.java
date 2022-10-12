package com.cgi.bonnie.authentication.auth;

public interface ApplicationUserDao {

    ApplicationUser selectApplicationUserByUsername(String username);

    ApplicationUser selectApplicationUserByEmail(String email);

}
