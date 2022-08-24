package com.cgi.hexagon.h2storage.user;

import com.cgi.hexagon.businessrules.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public AssemblyUser fromUser(User user) {
        return new AssemblyUser()
                .withId(user.getId())
                .withName(user.getName())
                .withPassword(user.getPassword())
                .withRole(user.getRole());
    }

    public User fromEntity(AssemblyUser userEntity) {
        return new User()
                .withId(userEntity.getId())
                .withName(userEntity.getName())
                .withPassword(userEntity.getPassword())
                .withRole(userEntity.getRole());
    }
}
