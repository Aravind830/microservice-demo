package com.sample.order_service.Service;

import com.sample.order_service.Entity.Order;
import com.sample.order_service.Entity.OrderItems;
import com.sample.order_service.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    public Order create(Order order){
        for (OrderItems item : order.getOrderItems()) {
            item.setOrder(order);
        }
        return orderRepository.save(order);
    }
}
