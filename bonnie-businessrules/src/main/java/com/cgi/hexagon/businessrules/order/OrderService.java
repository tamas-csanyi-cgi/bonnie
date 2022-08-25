package com.cgi.hexagon.businessrules.order;

import com.cgi.hexagon.businessrules.Status;
import com.cgi.hexagon.businessrules.user.UserService;

public class OrderService{

    final private IOrderService orderServiceIf;

    final private UserService userService;

    public OrderService(IOrderService loader, UserService userService) {
        this.orderServiceIf = loader;
        this.userService = userService;
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
