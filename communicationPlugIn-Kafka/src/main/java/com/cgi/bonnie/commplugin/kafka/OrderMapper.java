package com.cgi.bonnie.commplugin.kafka;

import com.cgi.bonnie.businessrules.order.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    public static Order fromOrderJson(OrderJson orderJson) {
        return new Order().withShopOderId(orderJson.getShopOrderId())
                .withGoodsId(orderJson.getGoodsId())
                .withQuantity(orderJson.getQuantity())
                .withPlacementDate(orderJson.getPlacementDate())
                .withMetadata(orderJson.getMetadataAsText());
    }

    public static List<Order> fromOrderJsonList(List<OrderJson> orderJsons) {
        return orderJsons.stream().map(OrderMapper::fromOrderJson).collect(Collectors.toList());
    }
}
