package com.cgi.bonnie.businessrules.user;

import com.cgi.bonnie.businessrules.Role;

public interface UserStorage {

    User load(long id);

    long createUser(String name, String password, Role role);

    boolean save(User user);

}
