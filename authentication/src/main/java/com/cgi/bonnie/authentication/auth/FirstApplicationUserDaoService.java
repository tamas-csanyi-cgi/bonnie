package com.cgi.bonnie.authentication.auth;

import com.cgi.bonnie.authentication.security.ApplicationUserRole;
import com.cgi.bonnie.h2storage.user.AssemblyUser;
import com.cgi.bonnie.h2storage.user.H2AssemblyUserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository()
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
        return Optional.ofNullable(fromAssemblyUser(userStorage.findByName(username)));
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByEmail(String email) {
        return Optional.ofNullable(fromAssemblyUser(userStorage.findByEmail(email)));
    }

    private List<ApplicationUser> getApplicationUsers() {
        return userStorage.findAll().stream().map(this::fromAssemblyUser).collect(Collectors.toList());
    }

    private ApplicationUser fromAssemblyUser(AssemblyUser user) {
        return new ApplicationUser(user.getName(),
                passwordEncoder.encode(user.getPassword()),
                user.getEmail(),
                ApplicationUserRole.valueOf(user.getRole().name()).getGrantedAuthorities(),
                true,
                true,
                true,
                true
        );
    }

}
