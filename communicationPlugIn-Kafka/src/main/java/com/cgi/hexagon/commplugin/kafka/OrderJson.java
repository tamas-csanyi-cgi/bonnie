package com.cgi.hexagon.commplugin.kafka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class OrderJson {
    String shopId;
    String goodsId;
    int quantity;
    JsonNode details;

    public OrderJson() {
    }

    @JsonCreator
    public OrderJson(@JsonProperty("shopId") String shopId,
                     @JsonProperty("goodsId") String goodsId,
                     @JsonProperty("quantity") int quantity,
                     @JsonProperty("details") JsonNode details) {
        this.shopId = shopId;
        this.goodsId = goodsId;
        this.quantity = quantity;
        this.details = details;
    }

    public String getShopId() {
        return shopId;
    }

    public OrderJson setShopId(String shopId) {
        this.shopId = shopId;
        return this;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public OrderJson setGoodsId(String goodsId) {
        this.goodsId = goodsId;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderJson setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public JsonNode getDetails() {
        return details;
    }

    public String getDetailsAsText() {
        return details.asText();
    }

    public OrderJson setDetails(JsonNode details) {
        this.details = details;
        return this;
    }
}
