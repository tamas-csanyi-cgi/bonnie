package com.cgi.hexagon.communicationplugin;

import com.cgi.hexagon.businessrules.Status;

import java.util.Map;

public class SendRequest {

    private String shopOrderId;

    private Status status;

    private String metadata;

    public SendRequest(String shopOrderId, Status status, String metadata) {
        this.shopOrderId = shopOrderId;
        this.status = status;
        this.metadata = metadata;
    }

    public SendRequest(String shopOrderId, Status status) {
        this.shopOrderId = shopOrderId;
        this.status = status;
    }

    public String getShopOrderId() {
        return shopOrderId;
    }

    public void setShopOrderId(String shopOrderId) {
        this.shopOrderId = shopOrderId;
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

    public SendRequest() {
    }

    @Override
    public String toString() {
        return "SendRequest{" +
                "\r\n    orderId='" + shopOrderId +'\'' +
                ",\n" +
                "    status=" + status +
                ",\n" +
                "    " + metadata.toString() +
                "\n}";
    }

}
