package com.labs.disruptor.order;


import lombok.Data;

/**
 * 订单消息类
 * @author panlf
 * @date 2026/5/11
 */
@Data
public class Order {
    private String id;
    private String name;
    private double price;
}
