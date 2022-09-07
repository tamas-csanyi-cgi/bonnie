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
                orderServiceIf.save(order);
                sender.send(fromOrder(order));
                return true;
            }
        }catch (Exception e) {}
        return false;
    }

    public boolean claimOrder(long orderId, long userId) {
        Order order = loadOrder(orderId);
        if (null == order.getAssembler() && order.getStatus() == Status.NEW) {
            User user = userService.loadUser(userId);
            if (null != user) {
                order.setAssembler("" + userId);
                order.setStatus(Status.CLAIMED);
                orderServiceIf.save(order);
                sender.send(fromOrder(order));
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
                order.setMetadata(new OrderMetadata(trackingNr));
                orderServiceIf.save(order);
                sender.send(fromOrder(order));
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
            sender.send(fromOrder(order));
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

    public SendRequest fromOrder(Order order){
        SendRequest sendRequest = new SendRequest();
        sendRequest.setOrderId(order.getShopId());
        sendRequest.setStatus(order.getStatus());
        sendRequest.setMetadata(order.getMetadata());
        return sendRequest;
    }

}
