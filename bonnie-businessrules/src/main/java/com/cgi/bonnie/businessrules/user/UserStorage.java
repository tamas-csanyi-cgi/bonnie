package com.cgi.bonnie.businessrules.user;

import java.util.List;

public interface UserStorage {

    User load(long id);

    long create(User user, String password);

    boolean save(User user);

    List<User> getAllUsers();

    User getUserByUsername(String username);

}
