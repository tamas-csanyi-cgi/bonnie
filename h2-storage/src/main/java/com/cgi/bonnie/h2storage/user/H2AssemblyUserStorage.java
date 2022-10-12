package com.cgi.bonnie.h2storage.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class H2AssemblyUserStorage {

    private H2UserRepository repository;

    public H2AssemblyUserStorage(H2UserRepository repository) {
        this.repository = repository;
    }

    public List<AssemblyUser> findAll() {
        return repository.findAll();
    }

    public AssemblyUser findByName(String name) {
        return repository.findByName(name);
    }

    public AssemblyUser findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
