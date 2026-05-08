package com.labs.disruptor.base;


import com.lmax.disruptor.EventFactory;

/**
 * 实例化LogEvent对象
 * @author panlf
 * @date 2026/5/8
 */
public class LogEventFactory implements EventFactory<LogEvent> {
    @Override
    public LogEvent newInstance() {
        return new LogEvent();
    }
}
