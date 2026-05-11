package com.labs.disruptor.order;


import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 *
 * @author panlf
 * @date 2026/5/11
 */
@Slf4j
public class OrderStart {
    public static void main(String[] args) throws Exception {
        //创建ringBuffer
        RingBuffer<Order> ringBuffer = RingBuffer.create(ProducerType.MULTI,
                Order::new,
                        1024 * 1024,
                        new YieldingWaitStrategy());

        SequenceBarrier barriers = ringBuffer.newBarrier();

        //创建3个消费者
        Consumer[] consumers = new Consumer[3];
        for(int i = 0; i < consumers.length; i++){
            consumers[i] = new Consumer("c" + i);
        }

        WorkerPool<Order> workerPool =
                new WorkerPool<>(ringBuffer,
                        barriers,
                        new EventExceptionHandler(),
                        consumers);


        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));


        final CountDownLatch latch = new CountDownLatch(1);

        //生产者
        for (int i = 0; i < 100; i++) {
            final Producer p = new Producer(ringBuffer);
            new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    log.error("出现异常：{}",e.getMessage());
                }
                for(int j = 0; j < 100; j ++){
                    p.sendData(UUID.randomUUID().toString());
                }
            }).start();
        }
        Thread.sleep(2000);
        log.info("---------------开始生产-----------------");
        latch.countDown();
        Thread.sleep(5000);
        log.info("{}：消费：{}",consumers[0],consumers[0].getCount());

    }

    static class EventExceptionHandler implements ExceptionHandler<Object> {
        @Override
        public void handleEventException(Throwable ex, long sequence, Object event) {}
        @Override
        public void handleOnStartException(Throwable ex) {}
        @Override
        public void handleOnShutdownException(Throwable ex) {}
    }

}


