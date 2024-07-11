package com.hewei.marketingmanage.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 消息记录（短信|微信|邮件）对象 dp_sys_sms
 * 
 * @author hewei
 * @date 2024-07-03
 */
public class DpSysSms extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 消息标题 */
    @Excel(name = "消息标题")
    private String esTitle;

    /** 发送方式：1短信 2邮件 3微信 */
    @Excel(name = "发送方式：1短信 2邮件 3微信")
    private String esType;

    /** 接收人 */
    @Excel(name = "接收人")
    private String esReceiver;

    /** 发送所需参数Json格式 */
    @Excel(name = "发送所需参数Json格式")
    private String esParam;

    /** 推送内容 */
    @Excel(name = "推送内容")
    private String esContent;

    /** 推送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "推送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date esSendTime;

    /** 推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送 */
    @Excel(name = "推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送")
    private String esSendStatus;

    /** 发送次数 超过5次不再发送 */
    @Excel(name = "发送次数 超过5次不再发送")
    private Long esSendNum;

    /** 推送失败原因 */
    @Excel(name = "推送失败原因")
    private String esResult;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setEsTitle(String esTitle) 
    {
        this.esTitle = esTitle;
    }

    public String getEsTitle() 
    {
        return esTitle;
    }
    public void setEsType(String esType) 
    {
        this.esType = esType;
    }

    public String getEsType() 
    {
        return esType;
    }
    public void setEsReceiver(String esReceiver) 
    {
        this.esReceiver = esReceiver;
    }

    public String getEsReceiver() 
    {
        return esReceiver;
    }
    public void setEsParam(String esParam) 
    {
        this.esParam = esParam;
    }

    public String getEsParam() 
    {
        return esParam;
    }
    public void setEsContent(String esContent) 
    {
        this.esContent = esContent;
    }

    public String getEsContent() 
    {
        return esContent;
    }
    public void setEsSendTime(Date esSendTime) 
    {
        this.esSendTime = esSendTime;
    }

    public Date getEsSendTime() 
    {
        return esSendTime;
    }
    public void setEsSendStatus(String esSendStatus) 
    {
        this.esSendStatus = esSendStatus;
    }

    public String getEsSendStatus() 
    {
        return esSendStatus;
    }
    public void setEsSendNum(Long esSendNum) 
    {
        this.esSendNum = esSendNum;
    }

    public Long getEsSendNum() 
    {
        return esSendNum;
    }
    public void setEsResult(String esResult) 
    {
        this.esResult = esResult;
    }

    public String getEsResult() 
    {
        return esResult;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("esTitle", getEsTitle())
            .append("esType", getEsType())
            .append("esReceiver", getEsReceiver())
            .append("esParam", getEsParam())
            .append("esContent", getEsContent())
            .append("esSendTime", getEsSendTime())
            .append("esSendStatus", getEsSendStatus())
            .append("esSendNum", getEsSendNum())
            .append("esResult", getEsResult())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
