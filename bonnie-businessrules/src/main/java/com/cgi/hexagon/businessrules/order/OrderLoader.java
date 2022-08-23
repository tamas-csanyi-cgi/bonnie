package com.cgi.hexagon.businessrules.order;

import com.cgi.hexagon.businessrules.Status;

public interface OrderLoader {

    Order load(long id);

    boolean releaseOrder(long id);

    boolean claimOrder(long id, long userId);

    boolean updateOrderStatus(long id, Status status);

    boolean setTrackingNumber(long id, String trackingNr);
}
