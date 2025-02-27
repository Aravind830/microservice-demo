package com.sample.order_service.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @OrderBy("order_items_id")
    private List<OrderItems> OrderItems;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<OrderItems> getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        OrderItems = orderItems;
    }
}
