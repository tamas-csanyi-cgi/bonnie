package com.cgi.hexagon.businessrules.user;

public class UserService {

    final private UserLoader loader;

    public UserService(UserLoader loader) {
        this.loader = loader;
    }

    public User loadUser(long id){
        return loader.load(id);
    }
}
