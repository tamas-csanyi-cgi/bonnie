package com.cgi.bonnie.h2storage.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface H2UserRepository extends CrudRepository<AssemblyUser, Long> {

    public AssemblyUser findByName(String username);

}
