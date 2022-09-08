package com.cgi.hexagon.businessrules.order;

import com.cgi.hexagon.businessrules.Status;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class Order {

    private long id;
    private String shopId;
    private String goodsId;
    private int quantity;
    private Status status;
    private String assembler;
    private Date realizationDate;

    private Map<String, Object> metadata;

    public Order() {
    }

    public Order(String shopId, String goodsId, int quantity) {
        this.shopId = shopId;
        this.goodsId = goodsId;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Order withId(long id) {
        this.setId(id);
        return this;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Order withShopId(String shopId) {
        this.setShopId(shopId);
        return this;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Order withGoodsId(String goodsId) {
        this.setGoodsId(goodsId);
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order withQuantity(int quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Order withStatus(Status status) {
        this.setStatus(status);
        return this;
    }

    public String getAssembler() {
        return assembler;
    }

    public void setAssembler(String assembler) {
        this.assembler = assembler;
    }

    public Order withAssembler(String assembler) {
        this.setAssembler(assembler);
        return this;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public Order withMetadata (Map<String, Object> metadata) {
        this.setMetadata(metadata);
        return this;
    }

    public Date getRealizationDate() {
        return realizationDate;
    }

    public void setRealizationDate(Date realizationDate) {
        this.realizationDate = realizationDate;
    }

    public Order withRealizationDate(Date realizationDate) {
        this.setRealizationDate(realizationDate);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(goodsId, order.goodsId) && status == order.status && Objects.equals(assembler, order.assembler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, goodsId, status, assembler);
    }
}
