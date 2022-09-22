package com.cgi.hexagon.businessrules.order;

import com.cgi.hexagon.businessrules.ISender;
import com.cgi.hexagon.businessrules.SendRequest;
import com.cgi.hexagon.businessrules.Status;
import com.cgi.hexagon.businessrules.user.User;
import com.cgi.hexagon.businessrules.user.UserService;

public class OrderService{

    final private IOrderService orderServiceIf;

    final private UserService userService;

    final private ISender sender;

    public OrderService(IOrderService loader, UserService userService, ISender sender) {
        this.orderServiceIf = loader;
        this.userService = userService;
        this.sender = sender;
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
                return orderServiceIf.save(order);
            }
        }catch (Exception e) {}
        return false;
    }

    public boolean claimOrder(long orderId, long userId) {
        Order order = loadOrder(orderId);
        if (order == null) {
            return false;
        }

        if (null == order.getAssembler() && order.status == Status.NEW) {
            User user = userService.loadUser(userId);
            if (null != user) {
                order.setAssembler(Long.toString(userId));
                order.setStatus(Status.CLAIMED);
                return orderServiceIf.save(order);
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
                if(orderServiceIf.save(order)) {
                    sender.send(new SendRequest(id, Status.SHIPPED, trackingNr));
                    return true;
                } else {
                    return false;
                }
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
            if(orderServiceIf.save(order)) {
                sender.send(new SendRequest(orderId, status));
                return true;
            } else {
                return false;
            }
        }catch (Exception e) {
            return false;
        }
    }

    public boolean finishOrder(long orderId) {
        Order order = loadOrder(orderId);
        if (order != null && order.getStatus() == Status.CLAIMED) {
            order.setStatus(Status.ASSEMBLED);
            if (orderServiceIf.save(order)) {
                sender.send(new SendRequest(orderId, Status.ASSEMBLED));
                return true;
            } else {
              return false;
            }

        }
        return false;
    }

}
