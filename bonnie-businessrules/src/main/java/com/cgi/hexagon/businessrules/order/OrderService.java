package com.cgi.hexagon.businessrules.order;

public class OrderService {

    final private IOrderService loader;

    public OrderService(IOrderService loader) {
        this.loader = loader;
    }

    public Order loadOrder(long id){
        return loader.load(id);
    }

    public boolean releaseOrder(long id) {
        return loader.releaseOrder(id);
    }

    public boolean claimOrder(long orderId, long userId) {
        return loader.claimOrder(orderId, userId);
    }



}
