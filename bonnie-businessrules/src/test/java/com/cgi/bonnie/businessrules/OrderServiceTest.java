package com.cgi.bonnie.businessrules;

import com.cgi.bonnie.businessrules.order.Order;
import com.cgi.bonnie.businessrules.user.AuthUserStorage;
import com.cgi.bonnie.businessrules.order.OrderService;
import com.cgi.bonnie.businessrules.order.OrderStorage;
import com.cgi.bonnie.businessrules.user.User;
import com.cgi.bonnie.businessrules.user.UserStorage;
import com.cgi.bonnie.communicationplugin.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    final long ORDER_ID = 1L;
    final long USER_ID = 1L;

    final String SHOP_ORDER_ID = "2022/Ord0001";

    final String TRACKING_NUMBER = "1";
    OrderStorage orderLoader;

    OrderService orderService;

    UserStorage userStorage;

    MessageService sender;

    AuthUserStorage authUserStorage;

    @BeforeEach
    public void setup() {
        orderLoader = Mockito.mock(OrderStorage.class);
        when(orderLoader.save(any())).thenReturn(true);
        userStorage = Mockito.mock(UserStorage.class);
        sender = Mockito.mock(MessageService.class);
        authUserStorage = Mockito.mock(AuthUserStorage.class);
        orderService = new OrderService(orderLoader, userStorage, sender, authUserStorage);
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

        assertTrue(orderService.releaseOrder(ORDER_ID), "Should return with true");
    }

    @Test
    public void expectReleaseClaimedOrderSetsAssemblerToNull() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED));

        orderService.releaseOrder(ORDER_ID);

        verify(orderLoader).save(argThat(order -> order.getAssignedTo() == null));
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

        assertFalse(orderService.releaseOrder(ORDER_ID), "Should return with false");
    }

    @Test
    public void expectReleaseNonExistingOrderReturnsFalse() {
        when(orderLoader.load(ORDER_ID)).thenReturn(null);

        assertFalse(orderService.releaseOrder(ORDER_ID), "Should return with false when order does not exists");
    }

    @Test
    public void expectReleaseReturnsFalseWhenSaveFails() {
        when(orderLoader.save(any())).thenReturn(false);
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED));

        assertFalse(orderService.releaseOrder(ORDER_ID));
    }

    @Test
    public void expectClaimOrderReturnsFalseWhenOrderDoesNotExist() {
        when(orderLoader.load(ORDER_ID)).thenReturn(null);

        assertFalse(orderService.claimOrder(ORDER_ID), "Should return with false");
    }

    @Test
    public void expectClaimNewOrderReturnsTrue() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.NEW));

        when(userStorage.load(USER_ID)).thenReturn(getUser());

        assertTrue(orderService.claimOrder(ORDER_ID));
    }

    @Test
    public void expectClaimNewOrderReturnsFalseWhenUserDoesNotExist() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.NEW));

        when(userStorage.load(USER_ID)).thenReturn(null);

        assertFalse(orderService.claimOrder(ORDER_ID));
    }

    @Test
    public void expectClaimNewOrderReturnsFalseWhenOrderStatusISNotNew() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.SHIPPED));

        when(userStorage.load(USER_ID)).thenReturn(getUser());

        assertFalse(orderService.claimOrder(ORDER_ID));
    }

    @Test
    public void expectClaimNewOrderReturnsFalseWhenThereIsAnAssembler() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withAssignedTo(getUser()));

        when(userStorage.load(USER_ID)).thenReturn(getUser());

        assertFalse(orderService.claimOrder(ORDER_ID));
    }

    @Test
    public void expectClaimOrderSavesWithAssembler() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder());

        when(userStorage.load(USER_ID)).thenReturn(getUser());

        orderService.claimOrder(ORDER_ID);

        verify(orderLoader).save(argThat(order -> USER_ID == order.getAssignedTo().getId()));
    }

    @Test
    public void expectClaimOrderSavesWithClaimedStatus() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder());

        when(userStorage.load(USER_ID)).thenReturn(getUser());

        orderService.claimOrder(ORDER_ID);

        verify(orderLoader).save(argThat(order -> order.getStatus() == Status.CLAIMED));
    }

    @Test
    public void expectClaimOrderReturnsFalseWhenSaveFails() {
        when(orderLoader.save(any())).thenReturn(false);
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder());
        when(userStorage.load(USER_ID)).thenReturn(getUser());

        assertFalse(orderService.claimOrder(ORDER_ID));
    }

    @Test
    public void expectSetTrackingNumberReturnsFalseWhenOrderDoesNotExists() {
        when(orderLoader.load(ORDER_ID)).thenReturn(null);

        assertFalse(orderService.setTrackingNumber(ORDER_ID, TRACKING_NUMBER));
    }

    @Test
    public void expectSetTrackingNumberReturnsFalseWhenOrderIsNotAssembled() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.NEW));

        assertFalse(orderService.setTrackingNumber(ORDER_ID, TRACKING_NUMBER));
    }

    @Test
    public void expectSetTrackingNumberSavesOrderWithTrackingNumber() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED));

        orderService.setTrackingNumber(ORDER_ID, TRACKING_NUMBER);

        verify(orderLoader).save(argThat(order -> order.getTrackingNr().equals(TRACKING_NUMBER)));
    }

    @Test
    public void expectSetTrackingNumberOrderReturnsFalseWhenSaveFails() {
        when(orderLoader.save(any())).thenReturn(false);
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED));

        assertFalse(orderService.setTrackingNumber(ORDER_ID, TRACKING_NUMBER));
    }

    @Test
    public void expectSetTrackingNumberOrderReturnsFalseWhenTNrIsNull() {
        assertFalse(orderService.setTrackingNumber(ORDER_ID, null));
    }

    @Test
    public void expectSetTrackingNumberOrderReturnsFalseWhenTNrIsEmpty() {
        assertFalse(orderService.setTrackingNumber(ORDER_ID, ""));
    }

    @Test
    public void expectSetTrackingNumberSavesOrderWithShippedStatus() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED));

        orderService.setTrackingNumber(ORDER_ID, TRACKING_NUMBER);

        verify(orderLoader).save(argThat(order -> order.getStatus() == Status.SHIPPED));
    }

    @Test
    public void expectSetTrackingNumberReturnsTrue() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED));

        assertTrue(orderService.setTrackingNumber(ORDER_ID, TRACKING_NUMBER));
    }

    @Test
    public void expectSetTrackingNumberCallsSender() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED));
        doNothing().when(sender).send(any());
        orderService.setTrackingNumber(ORDER_ID, TRACKING_NUMBER);

        verify(sender).send(argThat(sendRequest ->
                sendRequest.shopOrderId().equals(SHOP_ORDER_ID)
                        && sendRequest.status() == Status.SHIPPED
                        && TRACKING_NUMBER.equals(sendRequest.trackingNr())));
    }

    @Test
    public void expectUpdateStatusReturnsFalseWhenOrderDoesNotExist() {
        when(orderLoader.load(ORDER_ID)).thenReturn(null);

        assertFalse(orderService.updateStatus(ORDER_ID, Status.SHIPPED));
    }

    @Test
    public void expectUpdateStatusReturnsTrue() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder());

        when(userStorage.load(USER_ID)).thenReturn(getUser());

        when(orderLoader.save(any())).thenReturn(true);

        assertTrue(orderService.updateStatus(ORDER_ID, Status.SHIPPED));
    }

    @Test
    public void expectUpdateStatusSetsStatus() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder());

        when(userStorage.load(USER_ID)).thenReturn(getUser());

        orderService.updateStatus(ORDER_ID, Status.SHIPPED);

        verify(orderLoader).save(argThat(order -> order.getStatus() == Status.SHIPPED));
    }

    @Test
    public void expectUpdateStatusCallsSender() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder());

        when(userStorage.load(USER_ID)).thenReturn(getUser());

        when(orderLoader.save(any())).thenReturn(true);

        orderService.updateStatus(ORDER_ID, Status.SHIPPED);

        verify(sender).send(argThat(sendRequest ->
                sendRequest.status() == Status.SHIPPED && sendRequest.shopOrderId().equals(SHOP_ORDER_ID)));
    }

    @Test
    public void expectUpdateStatusReturnsFalseWhenSaveFails() {
        when(orderLoader.save(any())).thenReturn(false);
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder());

        assertFalse(orderService.updateStatus(ORDER_ID, Status.NEW));
    }

    @Test
    public void expectFinishOrderReturnsFalseWhenOrderDoesNotExist() {
        when(orderLoader.load(ORDER_ID)).thenReturn(null);

        assertFalse(orderService.finishOrder(ORDER_ID));
    }

    @Test
    public void expectFinishOrderReturnsTrue() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED));

        assertTrue(orderService.finishOrder(ORDER_ID));
    }

    @Test
    public void expectFinishOrderCallsSender() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED));

        orderService.finishOrder(ORDER_ID);

        verify(sender).send(argThat(sendRequest -> sendRequest.status() == Status.ASSEMBLED));
    }

    @Test
    public void expectFinishOrderReturnsFalseWhenStatusInNotClaimed() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.NEW));

        assertFalse(orderService.finishOrder(ORDER_ID));
    }

    @Test
    public void expectFinishOrderReturnsFalseWhenSaveFails() {
        when(orderLoader.save(any())).thenReturn(false);
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED));

        assertFalse(orderService.finishOrder(ORDER_ID));
    }

    @Test
    public void expectGetAllOrdersCallsFindAll() {
        orderService.getAllOrders();

        verify(orderLoader).findAll();
    }

    @Test
    public void expectCreateOrderReturnsErrorWhenQuantityIsInvalid() {
        assertEquals(-1, orderService.createOrder(getOrder().withQuantity(-2)));
    }

    @Test
    public void expectCreateOrderReturnsErrorWhenOrderIsNotNew() {
        assertEquals(-1, orderService.createOrder(getOrder().withId(4)));
    }

    @Test
    public void expectCreateOrderSuceedsWhenLoadFails() {
        doThrow(new IllegalStateException()).when(orderLoader).load(2);

        assertEquals(0, orderService.createOrder(getOrder().withId(2)));
    }

    @Test
    public void expectCreateOrderReturnsErrorShowOrderIdAlreadyExists() {
        when(orderLoader.findAllByShopOrderId(SHOP_ORDER_ID)).thenReturn(
                Arrays.asList(
                        getOrder(), getOrder()
                )
        );

        assertEquals(-1, orderService.createOrder(getOrder().withQuantity(3).withShopOrderId(SHOP_ORDER_ID)));
    }

    @Test
    public void expectCreateOrderSucceeds() {
        final long ORDER_ID2 = 23;
        when(orderLoader.create(any())).thenReturn(ORDER_ID2);

        assertEquals(ORDER_ID2, orderService.createOrder(getOrder()));
    }

    @Test
    public void expectCreateNewOrderCallsCreate() {
        final long ORDER_ID2 = 23;
        when(orderLoader.create(any())).thenReturn(ORDER_ID2);

        orderService.createOrder(getOrder());

        verify(orderLoader).create(argThat(order -> order.getStatus() == Status.NEW && order.getAssignedTo() == null));
    }

    @Test
    public void expectCreateOrdersCallsCreate() {
        orderService.createOrders(
                Arrays.asList(
                        getOrder(), getOrder()
                )
        );

        assertEquals(2, Mockito.mockingDetails(orderLoader)
                .getInvocations()
                .stream()
                .filter(invocation -> invocation.getMethod().getName().equals("create"))
                .count());
    }

    @Test
    public void expectGetUnclaimedOrderWhenFindAllByStatusNew() {
        List<Order> orders = new ArrayList<>();
        orders.add(getOrder());
        List<Order> toBeLoaded = orders;

        when(orderLoader.findAllByStatus(Status.NEW)).thenReturn(toBeLoaded);
        List<Order> loadedOrder = orderService.findAllByStatus(Status.NEW);
        assertEquals(toBeLoaded, loadedOrder);
    }

    private Order getOrder() {
        return new Order()
                .withStatus(Status.NEW)
                .withId(ORDER_ID)
                .withShopOrderId(SHOP_ORDER_ID)
                .withQuantity(2)
                .withGoodsId("awesome kit");
    }

    private User getUser() {
        return new User()
                .withId(USER_ID)
                .withName("user")
                .withRole(Role.ASSEMBLER);
    }
}