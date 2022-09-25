package com.cgi.hexagon.h2storage.order;

import com.cgi.hexagon.businessrules.Status;
import com.cgi.hexagon.h2storage.user.AssemblyUser;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AssemblyOrder {

    @Id
    @GeneratedValue
    long id;

    String shopId;

    String goodsId;

    int quantity;

    Status status;

    Long assembler;

    Date realizationDate;

    String metadata;

    public Date getRealizationDate() {
        return realizationDate;
    }

    public void setRealizationDate(Date realizationDate) {
        this.realizationDate = realizationDate;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public AssemblyOrder(long id) {
        this.id = id;
    }

    public AssemblyOrder() {
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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

    public AssemblyOrder withAssembler(Long assembler) {
        this.setAssembler(assembler);
        return this;
    }

    public AssemblyOrder withMetadata(String metadata) {
        this.setMetadata(metadata);
        return this;
    }

    public AssemblyOrder withRealizationDate(Date realizationDate) {
        this.setRealizationDate(realizationDate);
        return this;
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

    public Long getAssembler() {
        return assembler;
    }

    public void setAssembler(Long assembler) {
        this.assembler = assembler;
    }

}
