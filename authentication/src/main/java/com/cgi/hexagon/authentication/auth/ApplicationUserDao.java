package com.cgi.bonnie.authentication.auth;

import java.util.Optional;

public interface ApplicationUserDao {

    Optional<ApplicationUser> selectApplicationUserByUsername(String username);

    Optional<ApplicationUser> selectApplicationUserByEmail(String email);

}
