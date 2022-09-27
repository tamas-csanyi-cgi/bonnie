package com.cgi.hexagon.h2storage.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface H2OrderRepository extends CrudRepository<AssemblyOrder, Long> {

    //public AssemblyOrder findByAssembler(User assembler);
    public List<AssemblyOrder> findAllByShopOrderId( String shopOrderId);
}
