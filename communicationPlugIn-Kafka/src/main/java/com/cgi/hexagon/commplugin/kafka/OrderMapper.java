package com.cgi.hexagon.commplugin.kafka;

import com.cgi.hexagon.businessrules.order.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    public Order fromOrderJson( OrderJson orderJson){
        return new Order().withShopId( orderJson.getShopId())
                .withGoodsId(orderJson.getGoodsId())
                .withQuantity(orderJson.getQuantity())
                .withDetails( orderJson.getDetailsAsText());
    }

    public List<Order> fromOrderJsonList( List<OrderJson> orderJsons){
        List<Order> ret= new ArrayList<Order>();
        for ( OrderJson orderJson: orderJsons){
            ret.add( fromOrderJson( orderJson));
        }
        return ret;
    }
}
