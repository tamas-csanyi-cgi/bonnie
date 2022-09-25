package com.cgi.hexagon.h2storage.order;

import com.cgi.hexagon.businessrules.Status;
import com.cgi.hexagon.businessrules.order.Order;
import com.cgi.hexagon.businessrules.order.OrderStorage;
import com.cgi.hexagon.businessrules.user.User;
import com.cgi.hexagon.h2storage.user.H2UserStorage;
import com.cgi.hexagon.h2storage.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Primary
public class H2OrderStorage implements OrderStorage {

    private H2OrderRepository orderRepository;

    private H2UserStorage userStorage;

    private OrderMapper mapper;

    private UserMapper userMapper = new UserMapper();

    @Autowired
    public H2OrderStorage(H2OrderRepository orderRepository, H2UserStorage userStorage, OrderMapper mapper) {
        this.orderRepository = orderRepository;
        this.userStorage = userStorage;
        this.mapper = mapper;
    }

    public boolean save(Order o) {
        try {
            orderRepository.save(mapper.fromOrder(o));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public long create(Order order) {
        try {
            return orderRepository.save(mapper.fromOrder(order)).getId();
        } catch (Exception e) {
            return 0L;
        }
    }

    public Order findByAssembler(User assembler) {
        //return mapper.fromEntity(orderRepository.findByAssembler(assembler));
        return null;
    }

    public List<Order> findAll() {
        List<Order> result = new ArrayList<>();
        orderRepository.findAll().forEach(order -> result.add(mapper.fromEntity(order)));
        return result;
    }

    public List<Order> findAllByShopId(String shopId) {
        List<Order> result = new ArrayList<>();
        orderRepository.findAllByShopId(shopId).forEach(order -> result.add(mapper.fromEntity(order)));
        return result;
    }


    public Order load(long id) {
        return mapper.fromEntity(orderRepository.findById(id).orElseThrow(() -> new IllegalStateException("Order not found")));
    }

    @Override
    public boolean release(long id) {
        //fixme it has to implement in core
        if (orderRepository.findById(id).isPresent()) {
            AssemblyOrder order = orderRepository.findById(id).get();
            order.setAssembler(null);
            order.setStatus(Status.NEW);
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    @Override
    public boolean claim(long id, long userId) {
        //fixme it has to implement in core
        if (orderRepository.findById(id).isPresent()) {
            AssemblyOrder order = orderRepository.findById(id).get();
            if (null == order.getAssembler()) {
                order.setAssembler(userId);
                order.setStatus(Status.CLAIMED);
                orderRepository.save(order);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateStatus(long id, Status status) {
        if (orderRepository.findById(id).isPresent()) {
            //fixme it has to implement in core
            AssemblyOrder order = orderRepository.findById(id).get();
            order.setStatus(status);
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    @Override
    public boolean setTrackingNumber(long id, String trackingNr) {
        //fixme it has to implement in core
        if (orderRepository.findById(id).isPresent()) {
            AssemblyOrder order = orderRepository.findById(id).get();
            order.setStatus(Status.SHIPPED);
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    @Override
    public long create(String productId, int quantity, long assignedTo, Status status) {
        //fixme it has to implement in core.   Here should only store
        AssemblyOrder aOrder = new AssemblyOrder().withGoodsId(productId).withQuantity(quantity)
                .withAssembler(assignedTo).withStatus(status).withRealizationDate(new Date());
        orderRepository.save(aOrder);
        return aOrder.id;
    }
}
