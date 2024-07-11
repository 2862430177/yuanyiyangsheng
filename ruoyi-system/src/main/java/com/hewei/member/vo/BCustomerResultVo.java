package com.hewei.member.vo;

import com.hewei.member.domain.BCustomer;
import com.hewei.member.membercard.domain.BMemberCard;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 会员信息，附带会员卡信息
 * @Author hewei
 * @Date 2024/7/9 11:27
 */
@Data
public class BCustomerResultVo extends BCustomer {
    private BigDecimal remainingAmount;
    private BMemberCard memberCard;
}
