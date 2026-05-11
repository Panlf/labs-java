package com.labs.disruptor.order;


import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author panlf
 * @date 2026/5/11
 */
@Slf4j
public class Consumer implements WorkHandler<Order> {

    private final String consumerId;

    private static final AtomicInteger count = new AtomicInteger(0);

    public Consumer(String consumerId){
        this.consumerId = consumerId;
    }

    @Override
    public void onEvent(Order order) throws Exception {
        log.info("当前消费者：{}，消费信息：{}",this.consumerId,order.getId());
        count.incrementAndGet();
    }

    public int getCount(){
        return count.get();
    }
}
