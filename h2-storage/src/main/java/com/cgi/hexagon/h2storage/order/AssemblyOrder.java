package com.cgi.hexagon.h2storage.order;

import com.cgi.hexagon.businessrules.Status;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "assembly_order")
public class AssemblyOrder implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "shop_order_id")
    private String shopOrderId;

    @Column(name = "goods_id")
    private String goodsId;

    @Column(name = "quantity")
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "assigned_to")
    private Long assignedTo;

    @Column(name = "placement_date")
    private LocalDateTime placementDate;

    @Column(name = "tracking_nr")
    private String trackingNr;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    private String metadata;

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

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public AssemblyOrder withMetadata(String metadata) {
        this.setMetadata(metadata);
        return this;
    }

    public Long getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Long assignedTo) {
        this.assignedTo = assignedTo;
    }

    public AssemblyOrder withAssignedTo(Long assignedTo) {
        this.setAssignedTo(assignedTo);
        return this;
    }

    public LocalDateTime getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(LocalDateTime placementDate) {
        this.placementDate = placementDate;
    }

    public AssemblyOrder withPlacementDate(LocalDateTime placementDate) {
        this.setPlacementDate(placementDate);
        return this;
    }

    public String getShopOrderId() {
        return shopOrderId;
    }

    public void setShopOrderId(String shopOrderId) {
        this.shopOrderId = shopOrderId;
    }

    public AssemblyOrder withShopOderId(String shopOderId) {
        this.setShopOrderId(shopOderId);
        return this;
    }

    public String getTrackingNr() {
        return trackingNr;
    }

    public void setTrackingNr(String trackingNr) {
        this.trackingNr = trackingNr;
    }

    public AssemblyOrder withTrackingNr(String trackingNr) {
        this.setTrackingNr(trackingNr);
        return this;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public AssemblyOrder withLastUpdated(LocalDateTime lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }
}
