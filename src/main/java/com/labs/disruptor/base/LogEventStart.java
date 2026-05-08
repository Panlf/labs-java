package com.labs.disruptor.base;


import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

/**
 * 测试方法
 * @author panlf
 * @date 2026/5/8
 */
public class LogEventStart {
    public static void main(String[] args) {

        //创建工厂
        EventFactory<LogEvent> factory = new LogEventFactory();
        //创建bufferSize ,也就是RingBuffer大小，必须是2的N次方
        int ringBufferSize = 1024;


        //BlockingWaitStrategy 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现
        //WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy();
        //SleepingWaitStrategy 的性能表现跟BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景
        //WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy();
        //YieldingWaitStrategy 的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于CPU逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性
        //WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();

        Disruptor<LogEvent> disruptor =
                new Disruptor<>(factory, ringBufferSize,  DaemonThreadFactory.INSTANCE,
                        ProducerType.SINGLE, new BlockingWaitStrategy());
        // 连接消费事件方法
        disruptor.handleEventsWith(new LogEventHandler());

        // 启动
        disruptor.start();

        //Disruptor 的事件发布过程是一个两阶段提交的过程：
        //使用该方法获得具体存放数据的容器ringBuffer(环形结构)
        //发布事件
        RingBuffer<LogEvent> ringBuffer = disruptor.getRingBuffer();

        LogEventProducer producer = new LogEventProducer(ringBuffer);

        // 5. 发送测试消息
        producer.sendData("用户登录成功");
        producer.sendData("订单创建成功");
        producer.sendData("支付完成");

        // 6. 关闭
        disruptor.shutdown();
    }
}
