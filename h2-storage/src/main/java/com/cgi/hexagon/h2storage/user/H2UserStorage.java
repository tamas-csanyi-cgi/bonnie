package com.cgi.hexagon.h2storage.user;

import com.cgi.hexagon.businessrules.Role;
import com.cgi.hexagon.businessrules.user.User;
import com.cgi.hexagon.businessrules.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class H2UserStorage implements UserStorage {

    private H2UserRepository repository;
    private UserMapper mapper;

    @Autowired
    public H2UserStorage(H2UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public boolean save(User o) {
        try {
            repository.save(mapper.fromUser(o));
            return true;
        }catch (Exception e) {
            return false;
        }
    }


    @Override
    public User load(long id) {
        return mapper.fromEntity(repository.findById(id).orElseThrow(() -> new IllegalStateException("User not found")));
    }

    @Override
    public long createUser(String name, String password, Role role) {
        AssemblyUser user = new AssemblyUser().withName(name).withPassword(password).withRole(role);
        repository.save(user);
        return user.getId();
    }
}
