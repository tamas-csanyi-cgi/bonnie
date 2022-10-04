package com.cgi.bonnie.businessrules.user;

import com.cgi.bonnie.businessrules.Role;

public class UserService {

    final private UserStorage userServiceIf;

    public UserService(UserStorage loader) {
        this.userServiceIf = loader;
    }

    public User loadUser(long id){
        return userServiceIf.load(id);
    }

    public long createUser(String name, String password, Role role) {
        return userServiceIf.createUser(name, password, role);
    }

    public boolean save(User user) {
        return userServiceIf.save(user);
    }

}
