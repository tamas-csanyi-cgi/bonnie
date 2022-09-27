package com.cgi.hexagon.businessrules.user;

import com.cgi.hexagon.businessrules.Role;

import java.util.List;

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

    public List<User> getAllUsers() {return userServiceIf.getAllUsers();}

}
