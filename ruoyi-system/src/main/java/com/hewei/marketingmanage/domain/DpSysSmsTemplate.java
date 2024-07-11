package com.hewei.marketingmanage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 消息模版（短信|微信|邮件）对象 dp_sys_sms_template
 * 
 * @author hewei
 * @date 2024-07-03
 */
public class DpSysSmsTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 模板标题 */
    @Excel(name = "模板标题")
    private String templateName;

    /** 模板CODE */
    @Excel(name = "模板CODE")
    private String templateCode;

    /** 模板类型：1短信 2邮件 3微信 */
    @Excel(name = "模板类型：1短信 2邮件 3微信")
    private String templateType;

    /** 模板内容 */
    @Excel(name = "模板内容")
    private String templateContent;

    /** 模板测试json */
    @Excel(name = "模板测试json")
    private String templateTestJson;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTemplateName(String templateName) 
    {
        this.templateName = templateName;
    }

    public String getTemplateName() 
    {
        return templateName;
    }
    public void setTemplateCode(String templateCode) 
    {
        this.templateCode = templateCode;
    }

    public String getTemplateCode() 
    {
        return templateCode;
    }
    public void setTemplateType(String templateType) 
    {
        this.templateType = templateType;
    }

    public String getTemplateType() 
    {
        return templateType;
    }
    public void setTemplateContent(String templateContent) 
    {
        this.templateContent = templateContent;
    }

    public String getTemplateContent() 
    {
        return templateContent;
    }
    public void setTemplateTestJson(String templateTestJson) 
    {
        this.templateTestJson = templateTestJson;
    }

    public String getTemplateTestJson() 
    {
        return templateTestJson;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("templateName", getTemplateName())
            .append("templateCode", getTemplateCode())
            .append("templateType", getTemplateType())
            .append("templateContent", getTemplateContent())
            .append("templateTestJson", getTemplateTestJson())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
