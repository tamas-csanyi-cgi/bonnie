package com.cgi.hexagon.businessrules;

public class OrderService {

    final private OrderLoader loader;

    public OrderService(OrderLoader loader) {
        this.loader = loader;
    }

    public Order loadOrder(long id){
        return loader.load(id);
    }


}
