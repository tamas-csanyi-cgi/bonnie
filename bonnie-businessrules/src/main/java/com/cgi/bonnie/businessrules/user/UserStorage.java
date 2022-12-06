package com.cgi.bonnie.businessrules.user;

import java.util.List;

public interface UserStorage {

    User load(long id);

    long create(User user, String password);

    List<User> findAll();

    User findByUsername(String username);

    User findByEmail(String email);

}
