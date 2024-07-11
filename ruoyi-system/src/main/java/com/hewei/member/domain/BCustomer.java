package com.hewei.member.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员信息管理对象 b_customer
 * 
 * @author hewei
 * @date 2024-06-04
 */
public class BCustomer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 会员id */
    private Long id;

    /** 会员姓名 */
    @Excel(name = "会员姓名")
    private String customerName;

    /** 会员昵称 */
    @Excel(name = "会员昵称")
    private String nickName;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phonenumber;

    /** 用户性别（0男 1女 2未知） */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 帐号状态（0正常 1停用） */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 最后消费时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后消费时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUseDate;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }
    public void setPhonenumber(String phonenumber) 
    {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() 
    {
        return phonenumber;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setLastUseDate(Date lastUseDate) 
    {
        this.lastUseDate = lastUseDate;
    }

    public Date getLastUseDate() 
    {
        return lastUseDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerName", getCustomerName())
            .append("nickName", getNickName())
            .append("phonenumber", getPhonenumber())
            .append("sex", getSex())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("lastUseDate", getLastUseDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
