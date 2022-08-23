package com.cgi.hexagon.businessrules.order;

public class OrderService {

    final private OrderLoader loader;

    public OrderService(OrderLoader loader) {
        this.loader = loader;
    }

    public Order loadOrder(long id){
        return loader.load(id);
    }

    public boolean releaseOrder(long id) {
        return loader.releaseOrder(id);
    }

    public boolean claimOrder(long id) {
        return loader.releaseOrder(id);
    }


}
