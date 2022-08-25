package com.cgi.hexagon.businessrules.order;

import com.cgi.hexagon.businessrules.Status;

public interface IOrderService {

    Order load(long id);

    boolean save (Order order);

    boolean release(long id);

    boolean claim(long id, long userId);

    boolean updateStatus(long id, Status status);

    boolean setTrackingNumber(long id, String trackingNr);

    long create(String productId, int quantity, long assignedTo, Status status);
    
}
