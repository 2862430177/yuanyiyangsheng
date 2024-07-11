package com.ruoyi.quartz.task;

import com.alibaba.fastjson2.JSONObject;
import com.hewei.common.constant.DictConstants;
import com.hewei.marketingmanage.bo.SmsBatchChangeEvent;
import com.hewei.marketingmanage.domain.DpSysSmsBatch;
import com.hewei.marketingmanage.service.IDpSysSmsBatchService;
import com.hewei.member.service.IBCustomerService;
import com.hewei.member.vo.BCustomerQueryVo;
import com.hewei.member.vo.BCustomerResultVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description 短信|消息推送定时任务
 * @Author hewei
 * @Date 2024/7/8 16:29
 */
@Slf4j
@Component("smsTask")
@RequiredArgsConstructor
public class SmsTask {

    private final IDpSysSmsBatchService sysSmsBatchService;
    private final IBCustomerService bCustomerService;

    private final ApplicationContext applicationContext;

    public void runWithParams(String params)
    {
        log.info("短信|消息推送定时任务,调度开始：参数"+params);
        //获取需要执行的发送任务,只获取一个
        //状态为未开始
        DpSysSmsBatch earlyBatch = sysSmsBatchService.getEarlyBatchByExcuteDateTime(DictConstants.messageSendStatus_unbegin);
        if(earlyBatch ==null){
            log.info("没有要发送的消息批次了");
            return;
        }
        //获取发送条件并解析
        String filterConditions = earlyBatch.getFilterConditions();
        BCustomerQueryVo queryVo = JSONObject.parseObject(filterConditions,BCustomerQueryVo.class);
        //获取目标会员
        List<BCustomerResultVo> list = bCustomerService.selectBCustomerWithMemberCard(queryVo);
        if(CollectionUtils.isEmpty(list)){
            log.warn("没有获取到目标会员,跳过后续处理");
            return;
        }
        //开始发送前，将批次状态更新为发送中
        earlyBatch.setSendStatus(DictConstants.messageSendStatus_sending);
        sysSmsBatchService.updateDpSysSmsBatch(earlyBatch);
        //异步发送短信，并内存记录实时状态
        this.sendMessage(earlyBatch,list);


        //获取下一个最早的未发送批次
        DpSysSmsBatch nextEarlyBatch = sysSmsBatchService.getEarlyBatchByExcuteDateTime(DictConstants.messageSendStatus_unbegin);
        if(nextEarlyBatch != null){
            //发布消息变更事件
            applicationContext.publishEvent(new SmsBatchChangeEvent("消息批次变更"));
        }
    }

    @Async
    public void sendMessage(DpSysSmsBatch earlyBatch,List<BCustomerResultVo> list){
        if(CollectionUtils.isEmpty(list)){
            log.warn("没有获取到目标会员,跳过后续处理");
        }
        //短信模版（只能发送指定模版的内容，例：腾讯云需要提交模版审核后才能使用）
        String useTemplateContent = earlyBatch.getUseTemplateContent();
        log.info("使用短信模版useTemplateContent:"+useTemplateContent);
        //替换模版中的占位符 TODO
//        String replaceContent1 =  useTemplateContent.replaceAll()
//                .replaceAll()
//                .replaceAll()
//                .replaceAll();
//        for(BCustomerResultVo resultVo:list){
//            //接收人
//            String phoneNumber = resultVo.getPhonenumber();
//            //替换模版中的客户信息
//            String replaceContent2 = replaceContent1.replaceAll();
//            //一条一条的发
//            //调用腾讯云短信接口
//
//        }
        log.info("短信发送完毕，总条数:{},成功数:{},失败数:{}",list.size(),0,0);
        //发送完毕，更新批次发送状态
        earlyBatch.setSendStatus(DictConstants.messageSendStatus_success);
        sysSmsBatchService.updateDpSysSmsBatch(earlyBatch);
    }

}
