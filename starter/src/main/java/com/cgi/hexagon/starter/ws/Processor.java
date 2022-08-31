package com.cgi.hexagon.starter.ws;

import com.cgi.hexagon.bonnie.CreateOrderRequest;
import com.cgi.hexagon.businessrules.Receiver;
import com.cgi.hexagon.businessrules.order.Order;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Import(Receiver.class)
public class Processor {

    Receiver receiver;

    public void processOrders(List<CreateOrderRequest.Orders> request) {
        System.out.println("call arrived");
        receiver.saveOrders(map(request));
    }

    private List<Order> map(List<CreateOrderRequest.Orders> request) {
        return request.stream().map(m -> new Order(m.getShopId(), m.getProductId(), m.getQuantity())).collect(Collectors.toList());
    }

}
