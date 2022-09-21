package com.cgi.hexagon.businessrules.order;

import com.cgi.hexagon.businessrules.Status;

import java.util.List;

public interface IOrderService {

    Order load(long id);

    long save (Order order);

    List<Order> findAllByShopId(String shopId);

    boolean release(long id);

    boolean claim(long id, long userId);

    boolean updateStatus(long id, Status status);

    boolean setTrackingNumber(long id, String trackingNr);

    long create(String productId, int quantity, long assignedTo, Status status);
    
}
