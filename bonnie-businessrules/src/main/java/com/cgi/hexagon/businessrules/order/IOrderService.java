package com.cgi.hexagon.businessrules.order;

import com.cgi.hexagon.businessrules.Status;

public interface IOrderService {

    Order load(long id);

    boolean releaseOrder(long id);

    boolean claimOrder(long id, long userId);

    boolean updateOrderStatus(long id, Status status);

    boolean setTrackingNumber(long id, String trackingNr);

    long createOrder(String productId, int quantity, long assignedTo, Status status);
    
}
