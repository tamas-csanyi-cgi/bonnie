package com.cgi.bonnie.bonnierest.model;

public class OrderRequest {

    private String productId;

    private int quantity;

    private long assignedTo;

    private String status;

    public OrderRequest() {
    }

    public OrderRequest(String productId, int quantity, long assignedTo, String status) {
        this.productId = productId;
        this.quantity = quantity;
        this.assignedTo = assignedTo;
        this.status = status;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(long assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
