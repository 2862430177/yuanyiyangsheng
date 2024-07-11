package com.hewei.member.membercard.vo;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description 会员卡充值vo
 * @Author hewei
 * @Date 2024/6/5 21:08
 */
@Data
public class BMemberCardRechargeVo implements Serializable {
    /** 会员卡id */
    private Long id;

    /** 所属会员id */
    private Long customerId;
    /** 卡号（唯一） */
    private String cardno;

    /** 卡密 */
    private String cardSecret;

    /** 剩余金额，消费结算减少剩余金额，充值增加剩余金额 */
    private String remainingAmount;

    /** 本次充值金额 */
    private String rechargeAmount;
}
