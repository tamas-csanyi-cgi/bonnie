package com.cgi.hexagon.bonnierest;

import com.cgi.hexagon.bonnierest.model.OrderRequest;
import com.cgi.hexagon.businessrules.Status;
import com.cgi.hexagon.businessrules.order.Order;
import com.cgi.hexagon.businessrules.order.OrderService;
import com.cgi.hexagon.businessrules.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cgi.hexagon.businessrules.Status.NEW;


@RestController
@RequestMapping(value = "/api/order")
public class OrderController {

    private OrderService orderService;

    private UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable long id) {
        try {
            Order order = orderService.loadOrder(id);
            return ResponseEntity.ok(order);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getUnclaimedOrders")
    public ResponseEntity<List<Order>> findAllByStatus() {

        try {
            List<Order> orders = orderService.findAllByStatus(NEW);
            return ResponseEntity.ok(orders);
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
     }

    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest request) {
        try {
            Status status = Status.valueOf(request.getStatus().toUpperCase());
            long id = orderService.createOrder(request.getProductId(), request.getQuantity(), request.getAssignedTo(), status);
            return ResponseEntity.ok(""+id);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping(path = "/assign/{orderId}/{userId}")
    public ResponseEntity<Boolean> assignOrderToUser(@PathVariable long orderId, @PathVariable long userId) {
        boolean result = orderService.claimOrder(orderId, userId);
        return result ? ResponseEntity.ok(true):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @PatchMapping(path = "/release/{orderId}")
    public ResponseEntity<Boolean> releaseOrder(@PathVariable long orderId) {
        boolean result = orderService.releaseOrder(orderId);
        return result ? ResponseEntity.ok(true):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @PatchMapping(path = "/finish/{orderId}")
    public ResponseEntity<Boolean> finishOrder(@PathVariable long orderId) {
        boolean result = orderService.finishOrder(orderId);
        return result ? ResponseEntity.ok(true):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @PatchMapping(path = "/ship/{orderId}/{trackingNr}")
    public ResponseEntity<Boolean> shipOrder(@PathVariable long orderId, @PathVariable String trackingNr) {
        boolean result = orderService.setTrackingNumber(orderId, trackingNr);
        return result ? ResponseEntity.ok(true):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    public void receive() {

    }

}
