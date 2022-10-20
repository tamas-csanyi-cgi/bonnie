package com.cgi.bonnie.businessrules.user;

import java.util.List;

public interface UserCredentialStorage {

    List<UserCredentials> findAll();

    UserCredentials findByName(String name);

    UserCredentials findByEmail(String email);

}
