package com.cgi.bonnie.h2storage.order;

import com.cgi.bonnie.businessrules.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface H2OrderRepository extends CrudRepository<AssemblyOrder, Long> {

    //public AssemblyOrder findByAssembler(User assembler);
    public List<AssemblyOrder> findAllByShopOrderId( String shopOrderId);

    public List<AssemblyOrder> findAllByStatus(Status status);
}
