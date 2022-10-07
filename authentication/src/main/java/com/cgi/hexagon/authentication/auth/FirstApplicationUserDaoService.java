package com.cgi.bonnie.authentication.auth;

import com.cgi.bonnie.authentication.security.ApplicationUserRole;
import com.cgi.bonnie.h2storage.user.H2AssemblyUserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("first")
public class FirstApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    private final H2AssemblyUserStorage userStorage;

    @Autowired
    public FirstApplicationUserDaoService(PasswordEncoder passwordEncoder, H2AssemblyUserStorage userStorage) {
        this.passwordEncoder = passwordEncoder;
        this.userStorage = userStorage;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByEmail(String email) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> email.equals(applicationUser.getEmail()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> applicationUsers = new ArrayList<>();
        userStorage.getAssemblyUsers().stream().forEach(
                user -> applicationUsers.add(
                        new ApplicationUser(user.getName(),
                                            passwordEncoder.encode(user.getPassword()),
                                            user.getEmail(),
                                            ApplicationUserRole.valueOf(user.getRole().name()).getGrantedAuthorities(),
                                            true,
                                            true,
                                            true,
                                            true
                        )
                )
        );
        return applicationUsers;
    }

}
