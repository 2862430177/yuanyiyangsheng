package com.hewei.marketingmanage.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 短信消息批量发送定时执行状态对象 dp_sys_sms_batch
 * 
 * @author hewei
 * @date 2024-07-08
 */
@Data
public class DpSysSmsBatch extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 任务类型(SMS) */
    @Excel(name = "任务类型(SMS)")
    private String taskType;

    /** 计划执行时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划执行时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planExcuteDatetime;

    /** 发送状态(0未开始，1发送中，2发送成功，3发送失败，4部分成功) */
    @Excel(name = "发送状态(0未开始，1发送中，2发送成功，3发送失败，4部分成功)")
    private String sendStatus;

    /** 使用模版id */
    @Excel(name = "使用模版id")
    private Long useTemplateId;

    /** 使用模版内容 */
    @Excel(name = "使用模版内容")
    private String useTemplateContent;

    /** 过滤条件 */
    @Excel(name = "过滤条件")
    private String filterConditions;
}
