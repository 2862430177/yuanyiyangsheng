package com.hewei.marketingmanage.bo;

import org.springframework.context.ApplicationEvent;

/**
 * @Description 消息批次配置，变更事件
 * 事件发布时机：1新增批次、2修改批次、3批次执行完毕
 * @Author hewei
 * @Date 2024/7/10 9:38
 */
public class SmsBatchChangeEvent extends ApplicationEvent {

    public SmsBatchChangeEvent(Object source) {
        super(source);
    }
}
