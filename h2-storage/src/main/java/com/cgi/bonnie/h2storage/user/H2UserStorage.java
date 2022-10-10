package com.cgi.bonnie.h2storage.user;

import com.cgi.bonnie.businessrules.user.User;
import com.cgi.bonnie.businessrules.user.UserStorage;
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
        } catch (Exception e) {
            return false;
        }
    }

    public long create(User o, String password) {
        try {
            return repository.save(mapper.fromUser(o)).getId();
        } catch (Exception e) {
            return 0L;
        }
    }

    public User load(long id) {
        return mapper.fromEntity(repository.findById(id).orElseThrow(() -> new IllegalStateException("User not found")));
    }
}
