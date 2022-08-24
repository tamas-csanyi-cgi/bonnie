package com.cgi.hexagon.businessrules.user;

import com.cgi.hexagon.businessrules.Role;

import java.util.UUID;

public class UserService {

    final private IUserService userServiceIf;

    public UserService(IUserService loader) {
        this.userServiceIf = loader;
    }

    public User loadUser(long id){
        return userServiceIf.load(id);
    }

    public long createUser(String name, String password, Role role) {
        return userServiceIf.createUser(name, password, role);
    }
}
