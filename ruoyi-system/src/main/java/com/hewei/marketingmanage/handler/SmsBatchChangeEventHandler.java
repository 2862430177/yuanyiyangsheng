package com.hewei.marketingmanage.handler;

import com.hewei.marketingmanage.bo.SmsBatchChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Description 消息批次变更事件处理器,监听器
 * @Author hewei
 * @Date 2024/7/10 9:42
 */
@Component
public class SmsBatchChangeEventHandler implements ApplicationListener<SmsBatchChangeEvent> {
    @Override
    public void onApplicationEvent(SmsBatchChangeEvent event) {
        //获取将最早执行的消息批次（状态：未发送）
        //获取最早的未发送批次执行时间
        //根据执行时间，修改消息发送定时任务的下一次执行时间

    }
}
