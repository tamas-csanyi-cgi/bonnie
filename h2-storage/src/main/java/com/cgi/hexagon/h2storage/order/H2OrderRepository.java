package com.cgi.hexagon.h2storage.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface H2OrderRepository extends CrudRepository<AssemblyOrder, Long> {

    public AssemblyOrder findByAssembler(String assembler);

}
