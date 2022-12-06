package com.cgi.bonnie.bonnierest;

import com.cgi.bonnie.businessrules.order.Order;
import com.cgi.bonnie.businessrules.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cgi.bonnie.businessrules.Status.NEW;

@RestController
@RequestMapping(value = "/api/order")
public class OrderController {

    private final Logger log = LoggerFactory.getLogger(OrderController.class.getName());
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable long id) {
        try {
            Order order = orderService.loadOrder(id);
            return ResponseEntity.ok(order);
        } catch (IllegalStateException e) {
            log.error("can't load order: " + id + " (" + e.getMessage() + ")");
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("can't load order: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            log.error("can't load orders: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/new")
    public ResponseEntity<List<Order>> findAllNew() {
        try {
            List<Order> orders = orderService.findAllByStatus(NEW);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            log.error("can't find new orders: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping(path = "/assign/{orderId}")
    public ResponseEntity<Boolean> assignToMe(@PathVariable long orderId) {
        boolean result = orderService.claimOrder(orderId);
        return result ? ResponseEntity.ok(true) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @PatchMapping(path = "/release/{orderId}")
    public ResponseEntity<Boolean> releaseOrder(@PathVariable long orderId) {
        boolean result = orderService.releaseOrder(orderId);
        return result ? ResponseEntity.ok(true) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @PatchMapping(path = "/finish/{orderId}")
    public ResponseEntity<Boolean> finishOrder(@PathVariable long orderId) {
        boolean result = orderService.finishOrder(orderId);
        return result ? ResponseEntity.ok(true) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @PatchMapping(path = "/ship/{orderId}/{trackingNr}")
    public ResponseEntity<Boolean> shipOrder(@PathVariable long orderId, @PathVariable String trackingNr) {
        boolean result = orderService.setTrackingNumber(orderId, trackingNr);
        return result ? ResponseEntity.ok(true) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @GetMapping("/mine")
    public ResponseEntity<List<Order>> getMyOrders() {
        try {
            List<Order> orders = orderService.getMyOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            log.error("can't find orders assigned to current user: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
