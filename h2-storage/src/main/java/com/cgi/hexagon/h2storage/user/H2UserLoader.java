package com.cgi.hexagon.h2storage.user;

import com.cgi.hexagon.businessrules.Role;
import com.cgi.hexagon.businessrules.user.User;
import com.cgi.hexagon.businessrules.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

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

    @Override
    public long createUser(String name, String password, Role role) {
        AssemblyUser user = new AssemblyUser().withName(name).withPassword(password).withRole(role);
        repository.save(user);
        return user.getId();
    }
}
