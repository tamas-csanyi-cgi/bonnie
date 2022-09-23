package com.cgi.hexagon.businessrules;

import com.cgi.hexagon.businessrules.order.Order;
import com.cgi.hexagon.businessrules.order.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

public class ReceiverTest {

    Receiver receiver;

    OrderService orderService;

    @BeforeEach
    public void setup() {
        orderService = Mockito.mock(OrderService.class);
        receiver = new Receiver();
        receiver.os = orderService;
    }

    @Test
    public void expectSaveCallsSave() {
            receiver.saveOrders(Arrays.asList(
                    new Order("shopId1", "goodsId1", 1),
                    new Order("shopId2", "goodsId2", 1)
            ));

            assertEquals(2, Mockito.mockingDetails(orderService).getInvocations().size());
    }

}
