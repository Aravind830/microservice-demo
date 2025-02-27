package com.sample.order_service.Controller;

import com.sample.order_service.Entity.Order;
import com.sample.order_service.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAll(){
        return orderService.getAll();
    }

    @PostMapping("/save")
    public Order create(@RequestBody Order order){
        return orderService.create(order);
    }
}
