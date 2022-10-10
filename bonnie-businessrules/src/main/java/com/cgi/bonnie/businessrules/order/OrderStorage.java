package com.cgi.bonnie.businessrules.order;

import com.cgi.bonnie.businessrules.Status;

import java.util.List;

public interface OrderStorage {

    Order load(long id);

    boolean save(Order order);

    long create(Order order);

    List<Order> findAll();

    List<Order> findAllByShopOrderId(String shopOrderId);

    List<Order> findAllByStatus(Status status);
}
