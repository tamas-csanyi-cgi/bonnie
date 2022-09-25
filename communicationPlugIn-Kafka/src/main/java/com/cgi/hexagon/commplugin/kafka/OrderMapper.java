package com.cgi.hexagon.commplugin.kafka;

import com.cgi.hexagon.businessrules.order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderMapper {
    public Order fromOrderJson(OrderJson orderJson) {
        return new Order().withShopId(orderJson.getShopId())
                .withGoodsId(orderJson.getGoodsId())
                .withQuantity(orderJson.getQuantity())
                .withMetadata(orderJson.getMetadataAsText());
    }

    public List<Order> fromOrderJsonList(List<OrderJson> orderJsons) {
        List<Order> ret = new ArrayList<Order>();
        for (OrderJson orderJson : orderJsons) {
            ret.add(fromOrderJson(orderJson));
        }
        return ret;
    }
}
