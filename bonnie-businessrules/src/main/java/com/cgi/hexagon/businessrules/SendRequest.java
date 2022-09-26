package com.cgi.hexagon.businessrules;

public record SendRequest (
        long orderId,
        Status status,
        String trackingNr,
        String metadata
) {

    public SendRequest(long orderId, Status status) {
        this(orderId, status, null, null);
    }

}
