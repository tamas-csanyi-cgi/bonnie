package com.cgi.hexagon.businessrules.order;

import com.cgi.hexagon.businessrules.Status;
import com.cgi.hexagon.businessrules.user.User;
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
        try {
            Order order = loadOrder(id);
            if (order.getStatus() == Status.CLAIMED){
                order.setAssembler(null);
                order.setStatus(Status.NEW);
                orderServiceIf.save(order);
                return true;
            }
        }catch (Exception e) {}
        return false;
    }

    public boolean claimOrder(long orderId, long userId) {
        Order order = loadOrder(orderId);
        if (null == order.getAssembler() && order.status == Status.NEW) {
            User user = userService.loadUser(userId);
            if (null != user) {
                order.setAssembler("" + userId);
                order.setStatus(Status.CLAIMED);
                orderServiceIf.save(order);
                return true;
            }
        }
        return false;
    }

    public boolean setTrackingNumber(long id, String trackingNr) {
        try{
            Order order = loadOrder(id);
            if (order.getStatus() == Status.ASSEMBLED) {
                order.setStatus(Status.SHIPPED);
                order.setTrackingNr(trackingNr);
                orderServiceIf.save(order);
                return true;
            }
        }catch(Exception e) {}
        return false;
    }

    public long createOrder(String productId, int quantity, long assignedTo, Status status) {
        return orderServiceIf.create(productId, quantity, assignedTo, status);
    }

    public boolean updateStatus(long orderId, Status status) {
        try{
            Order order = loadOrder(orderId);
            order.setStatus(status);
            orderServiceIf.save(order);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public boolean finnishOrder(long orderId) {
        Order order = loadOrder(orderId);
        if (order.getStatus() == Status.CLAIMED) {
            updateStatus(orderId, Status.ASSEMBLED);
            return true;
        }
        return false;
    }

}
