package com.cgi.bonnie.authentication.auth;

import com.cgi.bonnie.businessrules.user.UserCredentialStorage;
import com.cgi.bonnie.businessrules.user.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import static com.cgi.bonnie.authentication.security.ApplicationUserRole.ASSEMBLER;

@Repository()
public class FirstApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    private final UserCredentialStorage userCredentialStorage;

    @Autowired
    public FirstApplicationUserDaoService(PasswordEncoder passwordEncoder, UserCredentialStorage userCredentialStorage) {
        this.passwordEncoder = passwordEncoder;
        this.userCredentialStorage = userCredentialStorage;
    }

    @Override
    public ApplicationUser selectApplicationUserByUsername(String username) {
        return fromUserCredentials(userCredentialStorage.findByName(username));
    }

    @Override
    public ApplicationUser selectApplicationUserByEmail(String email) {
        return fromUserCredentials(userCredentialStorage.findByEmail(email));
    }

    private ApplicationUser fromUserCredentials(UserCredentials user) {
        return new ApplicationUser(user.getName(),
                passwordEncoder.encode(user.getPassword()),
                user.getEmail(),
                ASSEMBLER.getGrantedAuthorities(),
                true,
                true,
                true,
                true
        );
    }

}
