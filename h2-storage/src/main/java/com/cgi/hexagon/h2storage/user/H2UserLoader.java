package com.cgi.hexagon.h2storage.user;

import com.cgi.hexagon.businessrules.user.User;
import com.cgi.hexagon.businessrules.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class H2UserLoader implements IUserService {

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
