package com.labs.disruptor.base;


import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 事件处理器
 * @author panlf
 * @date 2026/5/8
 */
@Slf4j
public class LogEventHandler implements EventHandler<LogEvent> {

    @Override
    public void onEvent(LogEvent logEvent, long sequence, boolean b) throws Exception {
        //接受消息
        log.info("【Disruptor消费消息】sequence= {}, 内容: {}",sequence,logEvent.getMessage());
    }
}
