package com.cgi.hexagon.h2storage.order;

import com.cgi.hexagon.businessrules.Status;
import com.cgi.hexagon.businessrules.order.IOrderService;
import com.cgi.hexagon.businessrules.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
public class H2OrderLoader implements IOrderService {

    private H2OrderRepository repository;

    private OrderMapper mapper;

    @Autowired
    public H2OrderLoader(H2OrderRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public long save(Order o) {
        try{
            repository.save(mapper.fromOrder(o));
            return o.getId();
        }catch (Exception e) {
            return 0;
        }
    }

    public Order findByAssembler(long assembler) {
        return mapper.fromEntity(repository.findByAssembler(""+assembler));
    }
    public List<Order> findAllByShopId(String shopId) {
        return mapper.fromEntities(repository.findAllByShopId(shopId));
    }

    @Override
    public Order load(long id) {
        return mapper.fromEntity(repository.findById(id).orElseThrow(() -> new IllegalStateException("Order not found")));
    }


    @Override
    public boolean release(long id) {
        if (repository.findById(id).isPresent()) {
            AssemblyOrder order = repository.findById(id).get();
            order.setAssembler(null);
            order.setStatus(Status.NEW);
            repository.save(order);
            return true;
        }
        return false;
    }

    @Override
    public boolean claim(long id, long userId) {
        if (repository.findById(id).isPresent()) {
            AssemblyOrder order = repository.findById(id).get();
            if (null == order.getAssembler()) {
                order.setAssembler("" + userId);
                order.setStatus(Status.CLAIMED);
                repository.save(order);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateStatus(long id, Status status) {
        if (repository.findById(id).isPresent()) {
            AssemblyOrder order = repository.findById(id).get();
            order.setStatus(status);
            repository.save(order);
            return true;
        }
        return false;
    }

    @Override
    public boolean setTrackingNumber(long id, String trackingNr) {
        if (repository.findById(id).isPresent()) {
            AssemblyOrder order = repository.findById(id).get();
            order.setStatus(Status.SHIPPED);
            order.setTrackingNr(trackingNr);
            repository.save(order);
            return true;
        }
        return false;
    }

    @Override
    public long create(String productId, int quantity, long assignedTo, Status status) {
        AssemblyOrder aOrder = new AssemblyOrder().withGoodsId(productId).withQuantity(quantity)
                .withAssembler(""+assignedTo).withStatus(status);
        repository.save(aOrder);
        return aOrder.id;
    }

}
