package com.cgi.hexagon.commplugin.kafka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class OrderJson {
    String shopOrderId;
    String goodsId;
    int quantity;

    JsonNode metadata;

    public OrderJson() {
    }

    @JsonCreator
    public OrderJson(@JsonProperty("shopOrderId") String shopOrderId,
                     @JsonProperty("goodsId") String goodsId,
                     @JsonProperty("quantity") int quantity,
                     @JsonProperty("metadata") JsonNode metadata) {
        this.shopOrderId = shopOrderId;
        this.goodsId = goodsId;
        this.quantity = quantity;
        this.metadata = metadata;
    }

    public String getShopOrderId() {
        return shopOrderId;
    }

    public OrderJson setShopOrderId(String shopOrderId) {
        this.shopOrderId = shopOrderId;
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

    public JsonNode getMetadata() {
        return metadata;
    }

    public String getMetadataAsText() {
        return metadata.toString();
    }

    public OrderJson setMetadata(JsonNode details) {
        this.metadata = details;
        return this;
    }
}
