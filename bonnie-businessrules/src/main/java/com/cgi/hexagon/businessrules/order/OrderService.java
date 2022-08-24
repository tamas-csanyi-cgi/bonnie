package com.cgi.hexagon.businessrules.order;

import com.cgi.hexagon.businessrules.Status;

public class OrderService{

    final private IOrderService orderServiceIf;

    public OrderService(IOrderService loader) {
        this.orderServiceIf = loader;
    }

    public Order loadOrder(long id){
        return orderServiceIf.load(id);
    }

    public boolean releaseOrder(long id) {
        return orderServiceIf.releaseOrder(id);
    }

    public boolean claimOrder(long orderId, long userId) {
        return orderServiceIf.claimOrder(orderId, userId);
    }

    public boolean setTrackingNumber(long id, String trackingNr) {
        return orderServiceIf.setTrackingNumber(id, trackingNr);
    }

    public long createOrder(String productId, int quantity, long assignedTo, Status status) {
        return orderServiceIf.createOrder(productId, quantity, assignedTo, status);
    }

}
