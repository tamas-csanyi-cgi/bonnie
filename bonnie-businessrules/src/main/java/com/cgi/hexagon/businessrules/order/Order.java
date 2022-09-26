package com.cgi.hexagon.businessrules.order;

import com.cgi.hexagon.businessrules.Status;
import com.cgi.hexagon.businessrules.user.User;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

    private long id;
    private String shopOderId;
    private String goodsId;
    private int quantity;
    private Status status;
    private User assignedTo;
    private LocalDateTime placementDate;
    private LocalDateTime lastUpdate;
    private String trackingNr;
    private String metadata;

    public Order() {
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

    public String getShopOderId() {
        return shopOderId;
    }

    public void setShopOderId(String shopOderId) {
        this.shopOderId = shopOderId;
    }

    public Order withShopOderId(String shopOderId) {
        setShopOderId(shopOderId);
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

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Order withAssignedTo(User assignedTo) {
        this.setAssignedTo(assignedTo);
        return this;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Order withMetadata(String metadata) {
        this.setMetadata(metadata);
        return this;
    }

    public LocalDateTime getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(LocalDateTime placementDate) {
        this.placementDate = placementDate;
    }

    public Order withPlacementDate(LocalDateTime placementDate) {
        this.setPlacementDate(placementDate);
        return this;
    }

    public String getTrackingNr() {
        return trackingNr;
    }

    public void setTrackingNr(String trackingNr) {
        this.trackingNr = trackingNr;
    }

    public Order withTrackingNr(String trackingNr) {
        this.setTrackingNr(trackingNr);
        return this;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Order withLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id
                && Objects.equals(goodsId, order.goodsId)
                && status == order.status
                && Objects.equals(assignedTo, order.assignedTo)
                && Objects.equals(shopOderId, order.shopOderId)
                && Objects.equals(metadata, order.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, goodsId, shopOderId, status, assignedTo, metadata);
    }
}
