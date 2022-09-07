package com.cgi.hexagon.businessrules.order;

import java.util.Date;

public class OrderMetadata {

    Date timestamp;
    String trackingNr;

    public OrderMetadata(String trackingNr) {
        this.timestamp = new Date();
        this.trackingNr = trackingNr;
    }

    public OrderMetadata() {
        this.timestamp = new Date();
    }

    public OrderMetadata(Date timestamp, String trackingNr) {
        this.timestamp = timestamp;
        this.trackingNr = trackingNr;
    }

    public OrderMetadata(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getTrackingNr() {
        return trackingNr;
    }

    public void setTrackingNr(String trackingNr) {
        this.trackingNr = trackingNr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OrderMetadata{").append("\n")
                .append("        timestamp=").append(timestamp).append("\n");
        if (trackingNr!=null) {
            sb.append("        trackingNr='").append(trackingNr).append("\'\n");
        }
        sb.append("    }");
        return sb.toString();
        /*return "OrderMetadata{" +
                "\n" +
                "        timestamp=" + timestamp +
                ",\n" +
                "        trackingNr='" + trackingNr + '\'' +
                "\n    }";*/
    }
}
