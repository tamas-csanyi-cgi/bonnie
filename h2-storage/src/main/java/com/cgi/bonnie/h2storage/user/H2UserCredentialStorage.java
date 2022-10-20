package com.cgi.bonnie.h2storage.user;

import com.cgi.bonnie.businessrules.user.UserCredentialStorage;
import com.cgi.bonnie.businessrules.user.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class H2UserCredentialStorage implements UserCredentialStorage {

    private final H2UserRepository repository;

    private final UserMapper mapper;

    @Autowired
    public H2UserCredentialStorage(H2UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<UserCredentials> findAll() {
        return repository.findAll().stream().map(mapper::credentialsFromAssemblyUser).collect(Collectors.toList());
    }

    @Override
    public UserCredentials findByName(String name) {
        return mapper.credentialsFromAssemblyUser(repository.findByName(name));
    }

    @Override
    public UserCredentials findByEmail(String email) {
        return mapper.credentialsFromAssemblyUser(repository.findByEmail(email));
    }
}
