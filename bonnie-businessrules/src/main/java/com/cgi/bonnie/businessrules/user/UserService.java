package com.cgi.bonnie.businessrules.user;

import com.cgi.bonnie.businessrules.Role;

import java.util.List;

public class UserService {

    final private UserStorage userStorage;

    final private AuthUserStorage authUserStorage;

    public UserService(UserStorage loader, AuthUserStorage storage) {
        this.userStorage = loader;
        this.authUserStorage = storage;
    }

    public User loadUser(long id) {
        return userStorage.load(id);
    }

    public long createUser(String name, String email, String password, Role role) {
        User user = new User().withName(name).withRole(role).withEmail(email);
        return userStorage.create(user, password);
    }

    public List<User> getAllUsers() {
        return userStorage.findAll();
    }

    public String getCurrentUsername() {
        return authUserStorage.getCurrentUsername();
    }

    public String getCurrentUserEmail() {
        return authUserStorage.getCurrentUserEmail();
    }

    public User getCurrentUser() {
        return userStorage.findByUsername(authUserStorage.getCurrentUsername());
    }

}
