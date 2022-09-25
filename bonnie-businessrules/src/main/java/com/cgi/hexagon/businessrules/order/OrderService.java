package com.cgi.hexagon.businessrules.order;

import com.cgi.hexagon.communicationplugin.MessageService;
import com.cgi.hexagon.communicationplugin.SendRequest;
import com.cgi.hexagon.businessrules.Status;
import com.cgi.hexagon.businessrules.user.User;
import com.cgi.hexagon.businessrules.user.UserStorage;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class OrderService {

    final private OrderStorage orderServiceIf;

    final private UserStorage userStorage;

    final private MessageService messageService;

    String pattern = "dd-MM-yyyy hh:mm:ss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public OrderService(OrderStorage loader, UserStorage userStorage, MessageService messageService) {
        this.orderServiceIf = loader;
        this.userStorage = userStorage;
        this.messageService = messageService;
     }

    public Order loadOrder(long id) {
        return orderServiceIf.load(id);
    }

    public List<Order> getAllOrders () {
        return orderServiceIf.findAll();
    }

    public boolean releaseOrder(long id) {
        try {
            Order order = loadOrder(id);
            if (order.getStatus() == Status.CLAIMED) {
                order.setAssembler(null);
                order.setStatus(Status.NEW);
       /*         Map<String, Object> metadata = new HashMap<>();
                metadata.put("lastUpdated", simpleDateFormat.format(new Date()));
                order.setMetadata(metadata);
        */        //FIXME what happens with the others, already stored and loaded metadata?
                if (orderServiceIf.save(order)) {
                    messageService.send(createSendRequest(order));
                return true;
            }
        }
        }catch (Exception e) {}
        return false;
    }

    public boolean claimOrder(long orderId, long userId) {
        Order order = loadOrder(orderId);
        if (null == order.getAssembler() && order.getStatus() == Status.NEW) {
            User user = userStorage.load(userId);
            if (null != user) {
                order.setAssembler(user);
                order.setStatus(Status.CLAIMED);
        /*        Map<String, Object> metadata = new HashMap<>();
                metadata.put("lastUpdated", simpleDateFormat.format(new Date()));
                order.setMetadata(metadata);
        */        //Fixme previous metadata
                if (orderServiceIf.save(order)) {
                    messageService.send(createSendRequest(order));
                return true;
            }
        }
        }
        return false;
    }

    public boolean setTrackingNumber(long id, String trackingNr) {
        if (null != trackingNr && !trackingNr.isEmpty()) {
        try {
            Order order = loadOrder(id);
            if (order.getStatus() == Status.ASSEMBLED) {
                order.setStatus(Status.SHIPPED);
             /*       Map<String, Object> metadata = new HashMap<>();
                    metadata.put("lastUpdated", simpleDateFormat.format(new Date()));
                    metadata.put("trackingNr", trackingNr);
                    order.setMetadata(metadata);
             */   //Fixme previous metadata
                    if(orderServiceIf.save(order)) {
                        messageService.send(createSendRequest(order));
                return true;
                    } else {
                        return false;
                    }
                }
            } catch(Exception e) {}
        }
        return false;
    }

    public long createOrder(String productId, int quantity, long assignedTo, Status status) {
        return orderServiceIf.create(productId, quantity, assignedTo, status);
    }

    public void createOrders(List<Order> orders) {
        for (Order order : orders) {
            createOrder(order);
        }
    }

    public long createOrder(Order order) {
        log.info( "orders arrived to Bonnie :-) ");
        if (!isNewOrder(order)) {
            log.error(" False or duplicated orderID in : {}", order.toString());
            return -1;
        }
        if (order.getQuantity() < 1) {
            log.error(" Invalid quantity in {}", order.toString());
            return -1;
        }
        if (orderServiceIf.findAllByShopId(order.getShopId()).size() > 0) {
            log.error(" ShopId [{}] already exists {}", order.getShopId(), order.toString());
            return -1;
        }
        order.setStatus(Status.NEW);
        order.setAssembler(null);
        return orderServiceIf.create(order);
    }

    public boolean isNewOrder(Order order) {
        if (order.getId() <= 1)
            return true;
        try {
            orderServiceIf.load(order.getId());
        } catch (IllegalStateException e) {
            return true;
        }
        return false;
    }

    public boolean updateStatus(long orderId, Status status) {
        try {
            Order order = loadOrder(orderId);
            order.setStatus(status);
           /* Map<String, Object> metadata = new HashMap<>();
            metadata.put("lastUpdated", simpleDateFormat.format(new Date()));
            order.setMetadata(metadata);
           */ //Fixme previous metadata
            if(orderServiceIf.save(order)) {
                messageService.send(new SendRequest(order.getShopId(), status));
            return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean finishOrder(long orderId) {
        Order order = loadOrder(orderId);
        if (order != null && order.getStatus() == Status.CLAIMED) {
            order.setStatus(Status.ASSEMBLED);
            if (orderServiceIf.save(order)) {
                messageService.send(new SendRequest(order.getShopId(), Status.ASSEMBLED));
            return true;
            } else {
        return false;
    }

        }
        return false;
    }

    public SendRequest createSendRequest(Order order){
        SendRequest sendRequest = new SendRequest();
        sendRequest.setShopOrderId(order.getShopId());
        sendRequest.setStatus(order.getStatus());
        sendRequest.setMetadata(order.getMetadata());
        return sendRequest;
    }

}
