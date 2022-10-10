package com.cgi.bonnie.businessrules.user;

public interface UserStorage {

    User load(long id);

    long create(User user, String password);

    boolean save(User user);
}
