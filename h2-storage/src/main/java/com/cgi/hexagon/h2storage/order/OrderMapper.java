package com.cgi.hexagon.h2storage.order;

import com.cgi.hexagon.businessrules.order.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    public AssemblyOrder fromOrder(Order order) {
        return new AssemblyOrder(order.getId())
                .withShopId(order.getShopId())
                .withStatus(order.getStatus())
                .withGoodsId(order.getGoodsId())
                .withAssembler(order.getAssembler())
                .withQuantity(order.getQuantity())
                .withTrackingNr(order.getTrackingNr())
                .withDetails(order.getDetails());
    }

    public Order fromEntity(AssemblyOrder assemblyOrder) {
        return new Order()
                .withId(assemblyOrder.getId())
                .withShopId(assemblyOrder.getShopId())
                .withStatus(assemblyOrder.getStatus())
                .withGoodsId(assemblyOrder.getGoodsId())
                .withAssembler(assemblyOrder.getAssembler())
                .withQuantity(assemblyOrder.getQuantity())
                .withTrackingNr(assemblyOrder.getTrackingNr())
                .withDetails(assemblyOrder.getDetails());
    }

    public List<AssemblyOrder> fromOrders(Iterable<Order> orders) {
        ArrayList<AssemblyOrder> ret= new ArrayList<>();
        orders.forEach(item-> ret.add(fromOrder(item)));
        return ret;
    }
    public List<Order> fromEntities(Iterable<AssemblyOrder> assemblyOrders) {
        ArrayList<Order> ret= new ArrayList<>();
        assemblyOrders.forEach(item-> ret.add(fromEntity(item)));
        return ret;
    }
}
