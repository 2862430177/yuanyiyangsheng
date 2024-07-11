package com.hewei.operatemanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 员工绩效指标结果VO
 * @Author hewei
 * @Date 2024/6/12 14:44
 */
@Data
public class EmployeePerformanceindicatorResultVo {

    /**
     * 日期
     */
//    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonIgnore
    private Date createTime;
    //员工id
    @JsonIgnore
    private Long employeeId;
    //项目金额
    private BigDecimal itemAmount;
    //产品金额
    private BigDecimal productAmount;
    //总金额=项目金额+产品金额
    private BigDecimal totalAmount;
    //实际消费金额
    private BigDecimal actualServiceCharge;
    //会员首次充值金额
    private BigDecimal membercardFirstAmount;
    //会员办卡续卡金额
    private BigDecimal cardRechargeAmount;
    //会员办卡提成
    private BigDecimal cardCommissionAmount;
    //项目提成
    private BigDecimal itemCommissionAmount;
    //产品提成
    private BigDecimal productCommissionAmount;
    //提成总计=会员办卡提成+项目提成+产品提成
    private BigDecimal commissionTotalAmount;
}
