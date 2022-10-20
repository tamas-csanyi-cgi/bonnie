package com.cgi.bonnie.h2storage.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface H2UserRepository extends CrudRepository<AssemblyUser, Long> {

    public AssemblyUser findByName(String username);

    public AssemblyUser findByEmail(String email);

    public List<AssemblyUser> findAll();

}
