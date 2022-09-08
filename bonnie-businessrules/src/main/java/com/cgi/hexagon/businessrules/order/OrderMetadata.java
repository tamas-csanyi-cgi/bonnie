package com.cgi.hexagon.businessrules.order;

import java.io.Serializable;
import java.util.Date;

public class OrderMetadata implements Serializable {

    Date lastUpdated;
    String trackingNr;
    String shippingAddress;


    public OrderMetadata(String trackingNr) {
        this.lastUpdated = new Date();
        this.trackingNr = trackingNr;
    }

    public OrderMetadata() {
        this.lastUpdated = new Date();
    }

    public OrderMetadata(Date lastUpdated, String trackingNr) {
        this.lastUpdated = lastUpdated;
        this.trackingNr = trackingNr;
    }

    public OrderMetadata(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getTrackingNr() {
        return trackingNr;
    }

    public void setTrackingNr(String trackingNr) {
        this.trackingNr = trackingNr;
    }

    public String getShippingAddress() { return shippingAddress;}

    public void setShippingAddress(String shippingAddress) {this.shippingAddress = shippingAddress;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("OrderMetadata{")
                .append("\n").append("        lastUpdated=").append(lastUpdated)
                .append(",\n").append("        shippingAddress='").append(shippingAddress).append('\'');

        if (trackingNr!=null) {
            sb.append(",\n").append("        trackingNr='").append(trackingNr).append('\'');
        }

        sb.append("\n").append("    }");
        return sb.toString();
    }
}
