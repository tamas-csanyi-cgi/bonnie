package com.cgi.hexagon.businessrules;

public class SendRequest {

    private long orderId;

    private Status status;

    private String trackingNr;

    public SendRequest(long orderId, Status status, String trackingNr) {
        this.orderId = orderId;
        this.status = status;
        this.trackingNr = trackingNr;
    }

    public SendRequest(long orderId, Status status) {
        this.orderId = orderId;
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTrackingNr() {
        return trackingNr;
    }

    public void setTrackingNr(String trackingNr) {
        this.trackingNr = trackingNr;
    }

    public SendRequest() {
    }
}
