package com.cgi.hexagon.h2storage.order;

import com.cgi.hexagon.businessrules.order.Order;
import com.cgi.hexagon.businessrules.order.OrderMetadata;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    MapConverter converter = new MapConverter();

    public AssemblyOrder fromOrder(Order order) {
        return new AssemblyOrder(order.getId())
                .withShopId(order.getShopId())
                .withStatus(order.getStatus())
                .withGoodsId(order.getGoodsId())
                .withAssembler(order.getAssembler())
                .withQuantity(order.getQuantity())
                .withMetadata(converter.convertToDatabaseColumn(order.getMetadata()))
                .withRealizationDate(order.getRealizationDate());
    }

    public Order fromEntity(AssemblyOrder orderEntity) {
        return new Order()
                .withId(orderEntity.getId())
                .withShopId(orderEntity.getShopId())
                .withStatus(orderEntity.getStatus())
                .withGoodsId(orderEntity.getGoodsId())
                .withAssembler(orderEntity.getAssembler())
                .withQuantity(orderEntity.getQuantity())
                .withMetadata(converter.convertToEntityAttribute(orderEntity.getMetadata()))
                .withRealizationDate(orderEntity.getRealizationDate());
    }
}
