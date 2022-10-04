package com.cgi.bonnie.h2storage.user;

import com.cgi.bonnie.businessrules.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public AssemblyUser fromUser(User user) {
        return new AssemblyUser()
                .withId(user.getId())
                .withName(user.getName())
                .withRole(user.getRole());
    }

    public User fromEntity(AssemblyUser userEntity) {
        return new User()
                .withId(userEntity.getId())
                .withName(userEntity.getName())
                .withRole(userEntity.getRole());
    }
}
