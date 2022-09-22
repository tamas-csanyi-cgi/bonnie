package com.cgi.hexagon.businessrules;

import com.cgi.hexagon.businessrules.order.Order;
import com.cgi.hexagon.businessrules.order.IOrderService;
import com.cgi.hexagon.businessrules.order.OrderService;
import com.cgi.hexagon.businessrules.user.User;
import com.cgi.hexagon.businessrules.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    final long ORDER_ID = 1L;
    final long USER_ID = 1L;

    final String TRACING_NUMBER = "1";

    IOrderService orderLoader;

    OrderService orderService;

    UserService userService;

    ISender sender;

    @BeforeEach
    public void setup() {
        orderLoader = Mockito.mock(IOrderService.class);
        when(orderLoader.save(any())).thenReturn(true);
        userService = Mockito.mock(UserService.class);
        sender = Mockito.mock(ISender.class);
        orderService = new OrderService(orderLoader, userService, sender);
    }

    @Test
    public void expectCallToALoaderWhenLoadIsCalled() {
        Order toBeLoaded = getOrder();

        when(orderLoader.load(ORDER_ID)).thenReturn(toBeLoaded);
        Order loadedOrder = orderService.loadOrder(ORDER_ID);
        assertEquals(toBeLoaded, loadedOrder, "Loaded order should be the same one we provided to the mock. ");
    }

    @Test
    public void expectReleaseClaimedOrderReturnsTrue() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED));

        assertEquals(true, orderService.releaseOrder(ORDER_ID), "Should return with true");
    }

    @Test
    public void expectReleaseClaimedOrderSetsAssemblerToNull() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED));

        orderService.releaseOrder(ORDER_ID);

        verify(orderLoader).save(argThat(order -> order.getAssembler() == null));
    }

    @Test
    public void expectReleaseClaimedOrderSetsStatusToNew() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED));

        orderService.releaseOrder(ORDER_ID);

        verify(orderLoader).save(argThat(order -> order.getStatus() == Status.NEW));
    }

    @Test
    public void expectReleaseUnClaimedOrderReturnsFalse() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.NEW));

        assertEquals(false, orderService.releaseOrder(ORDER_ID), "Should return with false");
    }

    @Test
    public void expectReleaseNonExistingOrderReturnsFalse() {
        when(orderLoader.load(ORDER_ID)).thenReturn(null);

        assertEquals(false, orderService.releaseOrder(ORDER_ID), "Should return with false when order does not exists");
    }

    @Test
    public void expectClaimOrderReturnsFalseWhenOrderDoesNotExist() {
        when(orderLoader.load(ORDER_ID)).thenReturn(null);

        assertEquals(false, orderService.claimOrder(ORDER_ID, USER_ID), "Should return with false");
    }

    @Test
    public void expectClaimNewOrderReturnsTrue() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.NEW));

        when(userService.loadUser(USER_ID)).thenReturn(getUser());

        assertEquals(true, orderService.claimOrder(ORDER_ID, USER_ID));
    }

    @Test
    public void expectClaimNewOrderReturnsFalseWhenUserDoesNotExist() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.NEW));

        when(userService.loadUser(USER_ID)).thenReturn(null);

        assertEquals(false, orderService.claimOrder(ORDER_ID, USER_ID));
    }

    @Test
    public void expectClaimNewOrderReturnsFalseWhenOrderStatusISNotNew() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.SHIPPED));

        when(userService.loadUser(USER_ID)).thenReturn(getUser());

        assertEquals(false, orderService.claimOrder(ORDER_ID, USER_ID));
    }

    @Test
    public void expectClaimNewOrderReturnsFalseWhenThereIsAnAssembler() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withAssembler("2"));

        when(userService.loadUser(USER_ID)).thenReturn(getUser());

        assertEquals(false, orderService.claimOrder(ORDER_ID, USER_ID));
    }

    @Test
    public void expectClaimOrderSavesWithAssembler() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder());

        when(userService.loadUser(USER_ID)).thenReturn(getUser());

        orderService.claimOrder(ORDER_ID, USER_ID);

        verify(orderLoader).save(argThat(order -> Long.toString(USER_ID).equals(order.getAssembler())));
    }

    @Test
    public void expectClaimOrderSavesWithClaimedStatus() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder());

        when(userService.loadUser(USER_ID)).thenReturn(getUser());

        orderService.claimOrder(ORDER_ID, USER_ID);

        verify(orderLoader).save(argThat(order -> order.getStatus() == Status.CLAIMED));
    }

    @Test
    public void expectSetTrackingNumberReturnsFalseWhenOrderDoesNotExists() {
        when(orderLoader.load(ORDER_ID)).thenReturn(null);

        assertEquals(false, orderService.setTrackingNumber(ORDER_ID, TRACING_NUMBER));
    }

    @Test
    public void expectSetTrackingNumberReturnsFalseWhenOrderIsNotAssembled() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.NEW));

        assertEquals(false, orderService.setTrackingNumber(ORDER_ID, TRACING_NUMBER));
    }

    @Test
    public void expectSetTrackingNumberSavesOrderWithTrackingNumber() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED));

        orderService.setTrackingNumber(ORDER_ID, TRACING_NUMBER);

       verify(orderLoader).save(argThat(order -> order.getTrackingNr() == TRACING_NUMBER));
    }

    @Test
    public void expectSetTrackingNumberSavesOrderWithShippedStatus() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED));

        orderService.setTrackingNumber(ORDER_ID, TRACING_NUMBER);

        verify(orderLoader).save(argThat(order -> order.getStatus() == Status.SHIPPED));
    }

    @Test
    public void expectSetTrackingNumberReturnsTrue() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED));

        assertEquals(true, orderService.setTrackingNumber(ORDER_ID, TRACING_NUMBER));
    }

    @Test
    public void expectSetTrackingNumberCallsSender() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED));

        orderService.setTrackingNumber(ORDER_ID, TRACING_NUMBER);

        verify(sender).send(argThat(sendRequest -> sendRequest.getOrderId() == ORDER_ID && sendRequest.getStatus() == Status.SHIPPED && TRACING_NUMBER.equals(sendRequest.getTrackingNr())));
    }

    @Test
    public void expectCreateOrderCallsCreate() {
        final String productId = "1";
        final int quantity = 1;
        final long assignedTo = 1L;
        final Status status = Status.NEW;

        orderService.createOrder(productId, quantity, assignedTo, status);

        verify(orderLoader).create(productId, quantity, assignedTo, status);
    }

    @Test
    public void expectUpdateStatusReturnsFalseWhenOrderDoesNotExist() {
        when(orderLoader.load(ORDER_ID)).thenReturn(null);

        assertEquals(false, orderService.updateStatus(ORDER_ID, Status.SHIPPED));
    }

    @Test
    public void expectUpdateStatusReturnsTrue() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder());

        when(userService.loadUser(USER_ID)).thenReturn(getUser());

        when(orderLoader.save(any())).thenReturn(true);

        assertEquals(true, orderService.updateStatus(ORDER_ID, Status.SHIPPED));
    }

    @Test
    public void expectUpdateStatusSetsStatus() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder());

        when(userService.loadUser(USER_ID)).thenReturn(getUser());

        orderService.updateStatus(ORDER_ID, Status.SHIPPED);

        verify(orderLoader).save(argThat(order -> order.getStatus() == Status.SHIPPED));
    }

    @Test
    public void expectUpdateStatusCallsSender() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder());

        when(userService.loadUser(USER_ID)).thenReturn(getUser());

        when(orderLoader.save(any())).thenReturn(true);

        orderService.updateStatus(ORDER_ID, Status.SHIPPED);

        verify(sender).send(argThat(sendRequest -> sendRequest.getStatus() == Status.SHIPPED && sendRequest.getOrderId() == ORDER_ID));
    }

    @Test
    public void expectFinishOrderReturnsFalseWhenOrderDoesNotExist() {
        when(orderLoader.load(ORDER_ID)).thenReturn(null);

        assertEquals(false, orderService.finishOrder(ORDER_ID));
    }

    @Test
    public void expectFinishOrderReturnsTrue() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED));

        assertEquals(true, orderService.finishOrder(ORDER_ID));
    }

    @Test
    public void expectFinishOrderCallsSender() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED));

        orderService.finishOrder(ORDER_ID);

        verify(sender).send(argThat(sendRequest -> sendRequest.getStatus() == Status.ASSEMBLED));
    }

    @Test
    public void expectFinishOrderReturnsFalseWhenStatusInNotClaimed() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.NEW));

        assertEquals(false, orderService.finishOrder(ORDER_ID));
    }

    private Order getOrder() {
        return new Order()
                .withStatus(Status.NEW)
                .withId(ORDER_ID)
                .withGoodsId("awesome kit");
    }

    private User getUser() {
        return new User()
                .withId(USER_ID)
                .withName("user")
                .withPassword("password")
                .withRole(Role.ASSEMBLER);
    }

}