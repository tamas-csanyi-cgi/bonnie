package com.cgi.hexagon.h2storage;

import com.cgi.hexagon.businessrules.Order;
import com.cgi.hexagon.businessrules.Status;
import org.springframework.data.annotation.Id;

public class AssemblyOrder{

    @Id
    long id;

    String shopId;

    String goodsId;

    int quantity;

    Status status;

    String assembler = null;

    public AssemblyOrder(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AssemblyOrder withId(long id) {
        this.setId(id);
        return this;
    }

    public AssemblyOrder withShopId(String shopId) {
        this.setShopId(shopId);
        return this;
    }

    public AssemblyOrder withGoodsId(String goodsId) {
        this.setGoodsId(goodsId);
        return this;
    }

    public AssemblyOrder withQuantity(int quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public AssemblyOrder withStatus(Status status) {
        this.setStatus(status);
        return this;
    }

    public AssemblyOrder withAssembler(String assembler) {
        this.setAssembler(assembler);
        return this;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAssembler() {
        return assembler;
    }

    public void setAssembler(String assembler) {
        this.assembler = assembler;
    }
}
