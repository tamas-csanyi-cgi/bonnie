package com.cgi.bonnie.businessrules.user;

public interface UserStorage {

    User load(long id);

    long create(User user, String password);

    User findByUsername(String username);

    User findByEmail(String email);

}
