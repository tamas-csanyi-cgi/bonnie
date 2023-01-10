package com.cgi.bonnie;

import com.cgi.bonnie.businessrules.order.Order;
import com.cgi.bonnie.businessrules.order.OrderService;
import com.cgi.bonnie.businessrules.order.OrderStorage;
import com.cgi.bonnie.communicationplugin.SendRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cgi.bonnie.businessrules.Status.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.securityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerIT extends BaseIT {

    private static final String PATH_ORDER_ROOT = "/api/order";
    private static final String PATH_ORDER_GET = PATH_ORDER_ROOT + "/{id}";
    private static final String PATH_ORDER_ALL_NEW = PATH_ORDER_ROOT + "/new";
    private static final String PATH_ORDER_ASSIGN_TO_ME = PATH_ORDER_ROOT + "/assign/{orderId}";
    private static final String PATH_ORDER_RELEASE = PATH_ORDER_ROOT + "/release/{orderId}";
    private static final String PATH_ORDER_FINISH = PATH_ORDER_ROOT + "/finish/{orderId}";
    private static final String PATH_ORDER_SHIP = PATH_ORDER_ROOT + "/ship/{orderId}/{trackingNumber}";
    private static final String PATH_ORDER_MINE = PATH_ORDER_ROOT + "/mine";
    private static final long UNKNOWN_ORDER_ID = 9999999L;
    private static final String TRACKING_NUMBER = "123-01";

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Value("${spring.bonnie.kafka.topic.message}")
    private String topicName;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderStorage orderStorage;

    private Consumer<String, SendRequest> consumerServiceTest;

    @BeforeAll
    void setup() {
        super.setup();

        final Map<String, Object> configs = new HashMap<>(KafkaTestUtils.consumerProps(
                "consumer",
                "false",
                embeddedKafkaBroker));

        final DefaultKafkaConsumerFactory<String, SendRequest> sendRequestConsumerFactory = new DefaultKafkaConsumerFactory<>(
                configs,
                new StringDeserializer(),
                new JsonDeserializer<>(SendRequest.class)
        );

        consumerServiceTest = sendRequestConsumerFactory.createConsumer();
        consumerServiceTest.subscribe(Collections.singletonList(topicName));
    }

    @AfterAll
    void teardown() {
        consumerServiceTest.close();
    }

    @Test
    void getOrder_unknownId_notFound() throws Exception {
        final MvcResult result = mockMvc.perform(get(PATH_ORDER_GET, UNKNOWN_ORDER_ID)
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        assertNotNull(result);
    }

    @Test
    void getOrder_validId_returnsOrder() throws Exception {
        final Order order = createOrder("AA01");

        final MvcResult result = mockMvc.perform(get(PATH_ORDER_GET, order.getId())
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());

        final Order orderResult = objectMapper.readValue(result.getResponse().getContentAsString(), Order.class);

        assertEquals(order.getId(), orderResult.getId());
        assertEquals(order.getShopOrderId(), orderResult.getShopOrderId());
        assertEquals(order.getGoodsId(), orderResult.getGoodsId());
        assertEquals(order.getQuantity(), orderResult.getQuantity());
        assertEquals(order.getStatus(), orderResult.getStatus());
        assertEquals(order.getAssignedTo(), orderResult.getAssignedTo());
        assertEquals(order.getPlacementDate(), orderResult.getPlacementDate());
        assertEquals(order.getLastUpdate(), orderResult.getLastUpdate());
        assertEquals(order.getTrackingNr(), orderResult.getTrackingNr());
        assertEquals(order.getMetadata(), orderResult.getMetadata());
    }

    @Test
    void findAllNew_noInputData_returnsNewOrderList() throws Exception {
        final MvcResult result = mockMvc.perform(get(PATH_ORDER_ALL_NEW)
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());

        final List<Order> orderListResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Order>>() {});

        assertFalse(orderListResult.isEmpty());
        assertTrue(orderListResult.stream().allMatch(order -> order.getStatus().equals(NEW)));
    }

    @Test
    void assignToMe_validOrderId_returnsOk() throws Exception {
        final Order order = createOrder("AB01");

        final MvcResult result = mockMvc.perform(patch(PATH_ORDER_ASSIGN_TO_ME, order.getId())
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        final ConsumerRecord<String, SendRequest> consumerRecord = KafkaTestUtils.getSingleRecord(consumerServiceTest, topicName);

        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertEquals(TRUE.toString(), result.getResponse().getContentAsString());

        final Order orderResult = orderService.loadOrder(order.getId());
        assertEquals(userData, orderResult.getAssignedTo());
        assertEquals(CLAIMED, orderResult.getStatus());

        final SendRequest sendRequestResult = consumerRecord.value();
        assertNotNull(sendRequestResult);
        assertEquals(order.getShopOrderId(), sendRequestResult.shopOrderId());
        assertEquals(CLAIMED, sendRequestResult.status());
        assertEquals(order.getMetadata(), sendRequestResult.metadata());
    }

    @Test
    void assignToMe_alreadyClaimedOrderId_badRequest() throws Exception {
        final Order order = createOrder("AB02");
        order.setStatus(CLAIMED);
        orderStorage.save(order);

        final MvcResult result = mockMvc.perform(patch(PATH_ORDER_ASSIGN_TO_ME, order.getId())
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertEquals(FALSE.toString(), result.getResponse().getContentAsString());

        final Order orderResult = orderService.loadOrder(order.getId());
        assertNotEquals(userData, orderResult.getAssignedTo());
    }

    @Test
    void releaseOrder_validOrderId_returnsOk() throws Exception {
        final Order order = createOrder("AC01");
        order.setAssignedTo(userData);
        order.setStatus(CLAIMED);
        orderStorage.save(order);

        final MvcResult result = mockMvc.perform(patch(PATH_ORDER_RELEASE, order.getId())
                            .with(securityContext(getSecurityContext())))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

        final ConsumerRecord<String, SendRequest> consumerRecord = KafkaTestUtils.getSingleRecord(consumerServiceTest, topicName);

        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertEquals(TRUE.toString(), result.getResponse().getContentAsString());

        final Order orderResult = orderService.loadOrder(order.getId());
        assertNull(orderResult.getAssignedTo());
        assertEquals(NEW, orderResult.getStatus());

        final SendRequest sendRequestResult = consumerRecord.value();
        assertNotNull(sendRequestResult);
        assertEquals(order.getShopOrderId(), sendRequestResult.shopOrderId());
        assertNull(sendRequestResult.trackingNr());
        assertEquals(NEW, sendRequestResult.status());
        assertEquals(order.getMetadata(), sendRequestResult.metadata());
    }

    @Test
    void releaseOrder_notClaimedOrderId_badRequest() throws Exception {
        final Order order = createOrder("AC02");

        final MvcResult result = mockMvc.perform(patch(PATH_ORDER_RELEASE, order.getId())
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertEquals(FALSE.toString(), result.getResponse().getContentAsString());
    }

    @Test
    void finishOrder_validOrderId_returnsOk() throws Exception {
        final Order order = createOrder("AD01");
        order.setAssignedTo(userData);
        order.setStatus(CLAIMED);
        orderStorage.save(order);

        final MvcResult result = mockMvc.perform(patch(PATH_ORDER_FINISH, order.getId())
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        final ConsumerRecord<String, SendRequest> consumerRecord = KafkaTestUtils.getSingleRecord(consumerServiceTest, topicName);

        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertEquals(TRUE.toString(), result.getResponse().getContentAsString());

        final Order orderResult = orderService.loadOrder(order.getId());
        assertEquals(ASSEMBLED, orderResult.getStatus());

        final SendRequest sendRequestResult = consumerRecord.value();
        assertNotNull(sendRequestResult);
        assertEquals(order.getShopOrderId(), sendRequestResult.shopOrderId());
        assertEquals(order.getTrackingNr(), sendRequestResult.trackingNr());
        assertNull(sendRequestResult.metadata());
        assertEquals(ASSEMBLED, sendRequestResult.status());
    }

    @Test
    void finishOrder_notClaimedOrderId_badRequest() throws Exception {
        final Order order = createOrder("AD02");

        final MvcResult result = mockMvc.perform(patch(PATH_ORDER_FINISH, order.getId())
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertEquals(FALSE.toString(), result.getResponse().getContentAsString());
    }

    @Test
    void shipOrder_validOrderIdAndTrackingNumber_returnsOk() throws Exception {
        final Order order = createOrder("AE01");
        order.setAssignedTo(userData);
        order.setStatus(ASSEMBLED);
        orderStorage.save(order);

        final MvcResult result = mockMvc.perform(patch(PATH_ORDER_SHIP, order.getId(), TRACKING_NUMBER)
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        final ConsumerRecord<String, SendRequest> consumerRecord = KafkaTestUtils.getSingleRecord(consumerServiceTest, topicName);

        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertEquals(TRUE.toString(), result.getResponse().getContentAsString());

        final Order orderResult = orderService.loadOrder(order.getId());
        assertEquals(SHIPPED, orderResult.getStatus());
        assertEquals(TRACKING_NUMBER, orderResult.getTrackingNr());

        final SendRequest sendRequestResult = consumerRecord.value();
        assertNotNull(sendRequestResult);
        assertEquals(order.getShopOrderId(), sendRequestResult.shopOrderId());
        assertEquals(SHIPPED, sendRequestResult.status());
        assertEquals(TRACKING_NUMBER, sendRequestResult.trackingNr());
        assertEquals(order.getMetadata(), sendRequestResult.metadata());
    }

    @Test
    void shipOrder_notAssembledOrderId_badRequest() throws Exception {
        final Order order = createOrder("AE02");

        final MvcResult result = mockMvc.perform(patch(PATH_ORDER_SHIP, order.getId(), TRACKING_NUMBER)
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertEquals(FALSE.toString(), result.getResponse().getContentAsString());
    }

    @Test
    void getMyOrders_noInputData_returnsMyOrders() throws Exception {
        final Order order = createOrder("AF01");
        order.setAssignedTo(userData);
        order.setStatus(CLAIMED);
        orderStorage.save(order);

        final MvcResult result = mockMvc.perform(get(PATH_ORDER_MINE)
                        .with(securityContext(getSecurityContext())))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertNotNull(result);
        assertNotNull(result.getResponse());
        assertNotNull(result.getResponse().getContentAsString());

        final List<Order> orderListResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Order>>() {});

        assertFalse(orderListResult.isEmpty());
        assertTrue(orderListResult.contains(order));
    }

    private Order createOrder(String shopOrderId) {
        final Order order = new Order();
        order.setShopOrderId("2022/00-" + shopOrderId);
        order.setGoodsId("B1 example item");
        order.setQuantity(1);
        order.setStatus(NEW);
        order.setPlacementDate(LocalDateTime.now());
        order.setLastUpdate(LocalDateTime.now());
        order.setMetadata("{\"shipping address\" : \"nowhere\"}");

        final long id = orderService.createOrder(order);
        return orderService.loadOrder(id);
    }
}
