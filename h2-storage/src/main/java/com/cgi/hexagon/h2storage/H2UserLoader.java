package com.cgi.hexagon.h2storage;

import com.cgi.hexagon.businessrules.User;
import com.cgi.hexagon.businessrules.UserLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class H2UserLoader implements UserLoader {

    private H2UserRepository repository;
    private UserMapper mapper;

    @Autowired
    public H2UserLoader(H2UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void save(User o) {
        repository.save(mapper.fromUser(o));
    }


    @Override
    public User load(long id) {
        return mapper.fromEntity(repository.findById(id).orElseThrow(() -> new IllegalStateException("User not found")));
    }
}
