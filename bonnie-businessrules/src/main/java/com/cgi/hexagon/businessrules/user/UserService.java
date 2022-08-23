package com.cgi.hexagon.businessrules.user;

public class UserService {

    final private IUserService loader;

    public UserService(IUserService loader) {
        this.loader = loader;
    }

    public User loadUser(long id){
        return loader.load(id);
    }
}
