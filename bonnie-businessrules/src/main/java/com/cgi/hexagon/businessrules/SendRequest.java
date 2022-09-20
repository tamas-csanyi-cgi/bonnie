package com.cgi.hexagon.businessrules;

import java.util.Map;

public class SendRequest {

    private long orderId;

    private Status status;

    private Map<String, Object> metadata;

    public SendRequest(long orderId, Status status, Map<String, Object> metadata) {
        this.orderId = orderId;
        this.status = status;
        this.metadata = metadata;
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

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public SendRequest() {
    }

    @Override
    public String toString() {
        return "SendRequest{" +
                "\r\n    orderId='" + orderId +'\'' +
                ",\n" +
                "    status=" + status +
                ",\n" +
                "    " + metadata.toString() +
                "\n}";
    }

}
