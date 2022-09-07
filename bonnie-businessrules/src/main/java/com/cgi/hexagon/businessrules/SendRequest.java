package com.cgi.hexagon.businessrules;

import com.cgi.hexagon.businessrules.order.Order;
import com.cgi.hexagon.businessrules.order.OrderMetadata;

public class SendRequest {

    private String orderId;

    private Status status;

    private OrderMetadata metadata;

    public SendRequest(String orderId, Status status, OrderMetadata metadata) {
        this.orderId = orderId;
        this.status = status;
        this.metadata = metadata;
    }

    public SendRequest(String orderId, Status status) {
        this.orderId = orderId;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public OrderMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(OrderMetadata metadata) {
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
