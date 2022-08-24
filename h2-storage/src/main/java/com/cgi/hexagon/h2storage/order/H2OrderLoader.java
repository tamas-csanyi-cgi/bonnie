package com.cgi.hexagon.h2storage.order;

import com.cgi.hexagon.businessrules.Role;
import com.cgi.hexagon.businessrules.Status;
import com.cgi.hexagon.businessrules.order.Order;
import com.cgi.hexagon.businessrules.order.IOrderService;
import com.cgi.hexagon.h2storage.user.AssemblyUser;
import com.cgi.hexagon.h2storage.user.H2UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

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

    public void save(Order o) {
        repository.save(mapper.fromOrder(o));
    }


    @Override
    public Order load(long id) {
        return mapper.fromEntity(repository.findById(id).orElseThrow(() -> new IllegalStateException("Order not found")));
    }

    @Override
    public boolean releaseOrder(long id) {
        if (repository.findById(id).isPresent()) {
            AssemblyOrder order = repository.findById(id).get();
            order.setAssembler(null);
            repository.save(order);
            return true;
        }
        return false;
    }

    @Override
    public boolean claimOrder(long id, long userId) {
        if (repository.findById(id).isPresent()) {
            AssemblyOrder order = repository.findById(id).get();
            order.setAssembler(""+userId);
            repository.save(order);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateOrderStatus(long id, Status status) {
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
            order.setTrackingNr(trackingNr);
            repository.save(order);
            return true;
        }
        return false;
    }

    @Override
    public long createOrder(String productId, int quantity, long assignedTo, Status status) {
        AssemblyOrder aOrder = new AssemblyOrder().withGoodsId(productId).withQuantity(quantity)
                .withAssembler(""+assignedTo).withStatus(status);
        repository.save(aOrder);
        return aOrder.id;
    }


}
