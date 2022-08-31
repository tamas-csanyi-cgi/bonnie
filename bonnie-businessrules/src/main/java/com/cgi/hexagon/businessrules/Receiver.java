package com.cgi.hexagon.businessrules;

import com.cgi.hexagon.businessrules.order.Order;
import com.cgi.hexagon.businessrules.order.OrderService;

import java.util.List;

public class Receiver{

    OrderService os;

    public void saveOrders(List<Order> orders) {
        for (Order o : orders) {
            os.createOrder(o.getGoodsId(), o.getQuantity(), 0, Status.NEW);
        }
    }
}
