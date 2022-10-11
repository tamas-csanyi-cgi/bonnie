package com.cgi.bonnie.businessrules;

import com.cgi.bonnie.businessrules.order.Order;
import com.cgi.bonnie.businessrules.user.AuthUserStorage;
import com.cgi.bonnie.businessrules.order.OrderService;
import com.cgi.bonnie.businessrules.order.OrderStorage;
import com.cgi.bonnie.businessrules.user.User;
import com.cgi.bonnie.businessrules.user.UserStorage;
import com.cgi.bonnie.communicationplugin.MessageService;
import com.cgi.bonnie.businessrules.user.AuthUserStorage;
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
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED).withAssignedTo(user));
        when(userStorage.getUserByUsername(any())).thenReturn(user);

        assertTrue(orderService.releaseOrder(ORDER_ID), "Should return with true");
    }

    @Test
    public void expectReleaseClaimedOrderSetsAssemblerToNull() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED).withAssignedTo(user));
        when(userStorage.getUserByUsername(any())).thenReturn(user);

        orderService.releaseOrder(ORDER_ID);

        verify(orderLoader).save(argThat(order -> order.getAssignedTo() == null));
    }

    @Test
    public void expectReleaseClaimedOrderSetsStatusToNew() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED).withAssignedTo(user));
        when(userStorage.getUserByUsername(any())).thenReturn(user);

        orderService.releaseOrder(ORDER_ID);

        verify(orderLoader).save(argThat(order -> order.getStatus() == Status.NEW));
    }

    @Test
    public void expectReleaseUnClaimedOrderReturnsFalse() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.NEW));
        when(userStorage.getUserByUsername(any())).thenReturn(getUser());

        assertFalse(orderService.releaseOrder(ORDER_ID), "Should return with false");
    }

    @Test
    public void expectReleaseNonExistingOrderReturnsFalse() {
        when(orderLoader.load(ORDER_ID)).thenReturn(null);
        when(userStorage.getUserByUsername(any())).thenReturn(getUser());

        assertFalse(orderService.releaseOrder(ORDER_ID), "Should return with false when order does not exists");
    }

    @Test
    public void expectReleaseReturnsFalseWhenSaveFails() {
        when(orderLoader.save(any())).thenReturn(false);
        when(userStorage.getUserByUsername(any())).thenReturn(getUser());
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED));

        assertFalse(orderService.releaseOrder(ORDER_ID));
    }

    @Test
    public void expectClaimOrderReturnsFalseWhenOrderDoesNotExist() {
        when(orderLoader.load(ORDER_ID)).thenReturn(null);
        when(userStorage.getUserByUsername(any())).thenReturn(getUser());

        assertFalse(orderService.claimOrder(ORDER_ID), "Should return with false");
    }

    @Test
    public void expectClaimNewOrderReturnsTrue() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.NEW));
        when(userStorage.getUserByUsername(any())).thenReturn(user);

        when(userStorage.load(USER_ID)).thenReturn(user);

        assertTrue(orderService.claimOrder(ORDER_ID));
    }

    @Test
    public void expectClaimNewOrderReturnsFalseWhenUserDoesNotExist() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.NEW));

        assertFalse(orderService.claimOrder(ORDER_ID));
    }

    @Test
    public void expectClaimNewOrderReturnsFalseWhenOrderStatusISNotNew() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.SHIPPED));

        when(userStorage.getUserByUsername(any())).thenReturn(getUser());

        assertFalse(orderService.claimOrder(ORDER_ID));
    }

    @Test
    public void expectClaimNewOrderReturnsFalseWhenThereIsAnAssembler() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withAssignedTo(user));

        when(userStorage.getUserByUsername(any())).thenReturn(user);

        assertFalse(orderService.claimOrder(ORDER_ID));
    }

    @Test
    public void expectClaimOrderSavesWithAssembler() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.NEW));

        when(userStorage.getUserByUsername(any())).thenReturn(user);

        orderService.claimOrder(ORDER_ID);

        verify(orderLoader).save(argThat(order -> USER_ID == order.getAssignedTo().getId()));
    }

    @Test
    public void expectClaimOrderSavesWithClaimedStatus() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.NEW));

        when(userStorage.getUserByUsername(any())).thenReturn(user);

        orderService.claimOrder(ORDER_ID);

        verify(orderLoader).save(argThat(order -> order.getStatus() == Status.CLAIMED));
    }

    @Test
    public void expectClaimOrderReturnsFalseWhenSaveFails() {
        User user = getUser();
        when(orderLoader.save(any())).thenReturn(false);
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withAssignedTo(user));
        when(userStorage.getUserByUsername(any())).thenReturn(user);

        assertFalse(orderService.claimOrder(ORDER_ID));
    }

    @Test
    public void expectSetTrackingNumberReturnsFalseWhenOrderDoesNotExists() {
        when(orderLoader.load(ORDER_ID)).thenReturn(null);
        when(userStorage.getUserByUsername(any())).thenReturn(getUser());

        assertFalse(orderService.setTrackingNumber(ORDER_ID, TRACKING_NUMBER));
    }

    @Test
    public void expectSetTrackingNumberReturnsFalseWhenOrderIsNotAssembled() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.NEW).withAssignedTo(user));
        when(userStorage.getUserByUsername(any())).thenReturn(user);

        assertFalse(orderService.setTrackingNumber(ORDER_ID, TRACKING_NUMBER));
    }

    @Test
    public void expectSetTrackingNumberSavesOrderWithTrackingNumber() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED).withAssignedTo(user));
        when(userStorage.getUserByUsername(any())).thenReturn(user);

        orderService.setTrackingNumber(ORDER_ID, TRACKING_NUMBER);

        verify(orderLoader).save(argThat(order -> order.getTrackingNr().equals(TRACKING_NUMBER)));
    }

    @Test
    public void expectSetTrackingNumberOrderReturnsFalseWhenSaveFails() {
        User user = getUser();
        when(orderLoader.save(any())).thenReturn(false);
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED).withAssignedTo(user));
        when(userStorage.getUserByUsername(any())).thenReturn(user);

        assertFalse(orderService.setTrackingNumber(ORDER_ID, TRACKING_NUMBER));
    }

    @Test
    public void expectSetTrackingNumberOrderReturnsFalseWhenTNrIsNull() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED).withAssignedTo(user));
        when(userStorage.getUserByUsername(any())).thenReturn(user);
        assertFalse(orderService.setTrackingNumber(ORDER_ID, null));
    }

    @Test
    public void expectSetTrackingNumberOrderReturnsFalseWhenTNrIsEmpty() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED).withAssignedTo(user));
        when(userStorage.getUserByUsername(any())).thenReturn(user);
        assertFalse(orderService.setTrackingNumber(ORDER_ID, ""));
    }

    @Test
    public void expectSetTrackingNumberSavesOrderWithShippedStatus() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED).withAssignedTo(user));
        when(userStorage.getUserByUsername(any())).thenReturn(user);
        orderService.setTrackingNumber(ORDER_ID, TRACKING_NUMBER);

        verify(orderLoader).save(argThat(order -> order.getStatus() == Status.SHIPPED));
    }

    @Test
    public void expectSetTrackingNumberReturnsTrue() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED).withAssignedTo(user));
        when(userStorage.getUserByUsername(any())).thenReturn(user);

        assertTrue(orderService.setTrackingNumber(ORDER_ID, TRACKING_NUMBER));
    }

    @Test
    public void expectSetTrackingNumberCallsSender() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED).withAssignedTo(user));
        when(userStorage.getUserByUsername(any())).thenReturn(user);
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
        when(userStorage.getUserByUsername(any())).thenReturn(getUser());

        assertFalse(orderService.updateStatus(ORDER_ID, Status.SHIPPED));
    }

    @Test
    public void expectUpdateStatusReturnsTrue() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withAssignedTo(user));

        when(userStorage.getUserByUsername(any())).thenReturn(user);

        when(orderLoader.save(any())).thenReturn(true);

        assertTrue(orderService.updateStatus(ORDER_ID, Status.SHIPPED));
    }

    @Test
    public void expectUpdateStatusSetsStatus() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED).withAssignedTo(user));

        when(userStorage.getUserByUsername(any())).thenReturn(user);

        orderService.updateStatus(ORDER_ID, Status.SHIPPED);

        verify(orderLoader).save(argThat(order -> order.getStatus() == Status.SHIPPED));
    }

    @Test
    public void expectUpdateStatusCallsSender() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.ASSEMBLED).withAssignedTo(user));

        when(userStorage.getUserByUsername(any())).thenReturn(user);

        when(orderLoader.save(any())).thenReturn(true);

        orderService.updateStatus(ORDER_ID, Status.SHIPPED);

        verify(sender).send(argThat(sendRequest ->
                sendRequest.status() == Status.SHIPPED && sendRequest.shopOrderId().equals(SHOP_ORDER_ID)));
    }

    @Test
    public void expectUpdateStatusReturnsFalseWhenSaveFails() {
        User user = getUser();
        when(orderLoader.save(any())).thenReturn(false);
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED).withAssignedTo(user));
        when(userStorage.getUserByUsername(any())).thenReturn(user);

        assertFalse(orderService.updateStatus(ORDER_ID, Status.NEW));
    }

    @Test
    public void expectFinishOrderReturnsFalseWhenOrderDoesNotExist() {
        when(orderLoader.load(ORDER_ID)).thenReturn(null);
        when(userStorage.getUserByUsername(any())).thenReturn(getUser());

        assertFalse(orderService.finishOrder(ORDER_ID));
    }

    @Test
    public void expectFinishOrderReturnsTrue() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED).withAssignedTo(user));
        when(userStorage.getUserByUsername(any())).thenReturn(user);

        assertTrue(orderService.finishOrder(ORDER_ID));
    }

    @Test
    public void expectFinishOrderCallsSender() {
        User user = getUser();
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED).withAssignedTo(user));
        when(userStorage.getUserByUsername(any())).thenReturn(user);

        orderService.finishOrder(ORDER_ID);

        verify(sender).send(argThat(sendRequest -> sendRequest.status() == Status.ASSEMBLED));
    }

    @Test
    public void expectFinishOrderReturnsFalseWhenStatusInNotClaimed() {
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.NEW));
        when(userStorage.getUserByUsername(any())).thenReturn(getUser());

        assertFalse(orderService.finishOrder(ORDER_ID));
    }

    @Test
    public void expectFinishOrderReturnsFalseWhenSaveFails() {
        User user = getUser();
        when(orderLoader.save(any())).thenReturn(false);
        when(orderLoader.load(ORDER_ID)).thenReturn(getOrder().withStatus(Status.CLAIMED).withAssignedTo(user));
        when(userStorage.getUserByUsername(any())).thenReturn(user);

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
                .withEmail("user@user.com")
                .withRole(Role.ASSEMBLER);
    }
}