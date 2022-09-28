package com.cgi.hexagon.h2storage.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class H2AssemblyUserStorage {

    private H2UserRepository repository;

    public H2AssemblyUserStorage(H2UserRepository repository) {
        this.repository = repository;
    }

    public List<AssemblyUser> getAssemblyUsers() {
        List<AssemblyUser> result = new ArrayList<>();
        repository.findAll().forEach(user -> result.add(user));
        return result;
    }
}
