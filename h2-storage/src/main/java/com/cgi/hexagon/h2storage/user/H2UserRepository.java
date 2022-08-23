package com.cgi.hexagon.h2storage.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface H2UserRepository extends CrudRepository<AssemblyUser, Long> {


}
