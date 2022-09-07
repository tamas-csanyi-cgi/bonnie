package com.cgi.hexagon.h2storage.order;

import com.cgi.hexagon.businessrules.order.Order;
import com.cgi.hexagon.businessrules.order.OrderMetadata;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public AssemblyOrder fromOrder(Order order) {
        return new AssemblyOrder(order.getId())
                .withShopId(order.getShopId())
                .withStatus(order.getStatus())
                .withGoodsId(order.getGoodsId())
                .withAssembler(order.getAssembler())
                .withQuantity(order.getQuantity());
    }

    public Order fromEntity(AssemblyOrder orderEntity) {
        return new Order()
                .withId(orderEntity.getId())
                .withShopId(orderEntity.getShopId())
                .withStatus(orderEntity.getStatus())
                .withGoodsId(orderEntity.getGoodsId())
                .withAssembler(orderEntity.getAssembler())
                .withQuantity(orderEntity.getQuantity())
                .withMetadata(new OrderMetadata());
    }
}
