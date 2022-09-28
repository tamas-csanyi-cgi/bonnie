package com.cgi.hexagon.businessrules.order;

import com.cgi.hexagon.businessrules.Status;

import java.util.List;

public interface OrderStorage {

    Order load(long id);

    boolean save(Order order);

    boolean release(long id);

    boolean claim(long id, long userId);

    boolean updateStatus(long id, Status status);

    boolean setTrackingNumber(long id, String trackingNr);

    long create(String productId, int quantity, long assignedTo, Status status);

    long create(Order order);

    List<Order> findAll();

    List<Order> findAllByShopOrderId(String shopOrderId);

    List<Order> findAllByAssembler(Long user);
}
