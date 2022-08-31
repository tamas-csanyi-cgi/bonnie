package com.cgi.hexagon.starter.ws;

import com.cgi.hexagon.bonnie.CreateOrderRequest;
import com.cgi.hexagon.bonnie.CreateOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class OrderEndpoint {

    //private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
    private static final String NAMESPACE_URI = "http://hexagon.cgi.com/bonnie";

    private Processor processor;

    @Autowired
    public OrderEndpoint(Processor processor) {
        this.processor = processor;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createOrderRequest")
    @ResponsePayload
    public CreateOrderResponse createOrder(@RequestPayload CreateOrderRequest request) {
        System.out.println("req arrived");
        CreateOrderResponse response = new CreateOrderResponse();
        processor.processOrders(request.getOrders());
        response.setData("someData");
        return response;
    }
}
