package com.cgi.bonnie.h2storage.user;

import com.cgi.bonnie.businessrules.user.User;
import com.cgi.bonnie.businessrules.user.UserCredentials;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public AssemblyUser assemblyUserFromUser(User user, String password) {
        return new AssemblyUser()
                .withId(user.getId())
                .withName(user.getName())
                .withRole(user.getRole())
                .withEmail(user.getEmail())
                .withPassword(password);
    }

    public User userFromAssemblyUser(AssemblyUser userEntity) {
        return new User()
                .withId(userEntity.getId())
                .withName(userEntity.getName())
                .withRole(userEntity.getRole())
                .withEmail(userEntity.getEmail());
    }

    public UserCredentials credentialsFromAssemblyUser(AssemblyUser userEntity) {
        return new UserCredentials(userEntity.getName(), userEntity.getEmail(), userEntity.getPassword());
    }


}
