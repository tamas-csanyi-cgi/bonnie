package com.cgi.bonnie.businessrules.user;

import com.cgi.bonnie.businessrules.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class.getName());

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
        if (null == findByEmail(email)) {
            User user = new User().withName(name).withRole(role).withEmail(email);
            return userStorage.create(user, password);
        }
        log.error("can't register user, because email address is already in use. (" + email + ")");
        return 0L;
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User findByEmail(String email) {
        return userStorage.findByEmail(email);
    }

    public String getCurrentUsername() {
        return authUserStorage.getCurrentUsername();
    }

    public String getCurrentUserEmail() {
        return authUserStorage.getCurrentUserEmail();
    }

    public User getCurrentUser() {
        return userStorage.findByEmail(authUserStorage.getCurrentUserEmail());
    }

}
