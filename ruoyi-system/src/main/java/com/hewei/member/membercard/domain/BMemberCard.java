package com.hewei.member.membercard.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员卡信息管理对象 b_member_card
 * 
 * @author hewei
 * @date 2024-06-04
 */
@Data
public class BMemberCard extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 会员卡id */
    private Long id;

    /** 所属会员id */
    @Excel(name = "所属会员id")
    private Long customerId;

    /** 卡号（唯一） */
    @Excel(name = "卡号", readConverterExp = "唯=一")
    private String cardno;

    /** 卡密 */
    @Excel(name = "卡密")
    private String cardSecret;

    /** 初始金额，首次充值金额 */
    @Excel(name = "初始金额，首次充值金额")
    private BigDecimal initAmount;

    /** 剩余金额，消费结算减少剩余金额，充值增加剩余金额 */
    @Excel(name = "剩余金额，消费结算减少剩余金额，充值增加剩余金额")
    private BigDecimal remainingAmount;

    /** 会员卡等级（1青铜 2白银 3黄金 4钻石） */
    @Excel(name = "会员卡等级", readConverterExp = "1=青铜,2=白银,3=黄金,4=钻石")
    private String level;

    /** 会员卡状态（0正常 1停用） */
    @Excel(name = "会员卡状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 上次充值时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "上次充值时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastRechargeTime;

    /** 上次消费时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "上次消费时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastCostTime;

    /** 推荐人（员工id） */
    @Excel(name = "推荐人", readConverterExp = "员工id")
    private Long recommendEmployeeId;

    /** 推荐人（会员id） */
    @Excel(name = "推荐人", readConverterExp = "会员id")
    private Long recommendCustomerId;
}
