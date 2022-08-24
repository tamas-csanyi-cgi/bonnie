package com.cgi.hexagon.businessrules.user;

import com.cgi.hexagon.businessrules.Role;

import java.util.UUID;

public interface IUserService {

    User load(long id);

    long createUser(String name, String password, Role role);
}
