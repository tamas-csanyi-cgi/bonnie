package com.cgi.hexagon.h2storage;

import com.cgi.hexagon.businessrules.Order;
import com.cgi.hexagon.businessrules.OrderLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class H2OrderLoader implements OrderLoader {

    private H2OrderRepository repository;
    private OrderMapper mapper;

    @Autowired
    public H2OrderLoader(H2OrderRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void save(Order o) {
        repository.save(mapper.fromOrder(o));
    }



    @Override
    public Order load(long id) {
        return mapper.fromEntity(repository.findById(id).orElseThrow(() -> new IllegalStateException("Order not found")));
    }




}
