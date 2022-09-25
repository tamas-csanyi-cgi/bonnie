package com.cgi.hexagon.businessrules.order;

import com.cgi.hexagon.businessrules.MessageService;
import com.cgi.hexagon.businessrules.SendRequest;
import com.cgi.hexagon.businessrules.Status;
import com.cgi.hexagon.businessrules.user.User;
import com.cgi.hexagon.businessrules.user.UserStorage;

import java.time.LocalDateTime;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderService {

    final private OrderStorage orderServiceIf;

    final private UserStorage userStorage;

    final private MessageService messageService;

    public OrderService(OrderStorage loader, UserStorage userStorage, MessageService messageService) {
        this.orderServiceIf = loader;
        this.userStorage = userStorage;
        this.messageService = messageService;
    }

    public Order loadOrder(long id) {
        return orderServiceIf.load(id);
    }

    public List<Order> getAllOrders() {
        return orderServiceIf.findAll();
    }

    public boolean releaseOrder(long id) {
        try {
            Order order = loadOrder(id);
            if (order.getStatus() == Status.CLAIMED) {
                order.setAssembler(null);
                order.setStatus(Status.NEW);
                order.setLastUpdate(LocalDateTime.now());
                if (orderServiceIf.save(order)) {
                    messageService.send(createSendRequest(order));
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean claimOrder(long orderId, long userId) {
        Order order = loadOrder(orderId);
        if (order == null) {
            return false;
        }
        if (null == order.getAssignedTo() && order.getStatus() == Status.NEW) {
            User user = userStorage.load(userId);
            if (null != user) {
                order.setAssignedTo(user);
                order.setStatus(Status.CLAIMED);
                order.setLastUpdate(LocalDateTime.now());
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
                    order.setTrackingNr(trackingNr);
                    order.setLastUpdate(LocalDateTime.now());
                    if (orderServiceIf.save(order)) {
                        messageService.send(createSendRequest(order));
                        return true;
                    } else {
                        return false;
                    }
                }
            } catch (Exception e) {
            }
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
        log.debug("orders arrived to Bonnie :-) ");
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
        long id = orderServiceIf.save(order);
        if (id > 0L) {
            order.setId(id);
            log.debug("Order is created {}", order.toString());
        } else
            log.error("Can't created an order: {}", order.toString());
        return id;
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
        try{
            Order order = loadOrder(orderId);
            order.setStatus(status);
            order.setLastUpdate(LocalDateTime.now());
            if(orderServiceIf.save(order)) {
                messageService.send(new SendRequest(orderId, status));
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
                messageService.send(new SendRequest(orderId, Status.ASSEMBLED));
                return true;
            } else {
              return false;
            }

        }
        return false;
    }

    public SendRequest createSendRequest(Order order){
        return new SendRequest(order.getId(), order.getStatus(), order.getTrackingNr(), order.getMetadata());
    }

}
