package com.hewei.dailyservice.serviceflow.domain;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 服务流水,一次服务一条流水记录对象 b_service_flow
 * 
 * @author hewei
 * @date 2024-06-05
 */
@Data
public class BServiceFlow extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 服务流水id */
    private Long id;

    /** 服务员工id */
    @Excel(name = "服务员工id")
    private Long employeeId;

    /** 服务设备id */
    @Excel(name = "服务设备id")
    private Long equipmentId;

    /** 客户会员id */
    @Excel(name = "客户会员id")
    private Long customerId;

    /** 本次服务使用会员卡id */
    @Excel(name = "本次服务使用会员卡id")
    private Long memberCardId;

    /** 结算方式(0现金、1会员卡) */
    @Excel(name = "结算方式",readConverterExp="0现金、1会员卡")
    private String settlementType;

    /** 服务总收费 */
    @Excel(name = "服务总收费")
    private BigDecimal serviceCharge;

    /** 服务状态（0服务中 1已取消 2、已结算 3、已结束(非正常结算)） 4已重新结算*/
    @Excel(name = "服务状态", readConverterExp = "0=服务中,1=已取消,2=、已结算,3=、已结束(非正常结算)")
    private String status;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 服务流水,一次服务一条流水记录信息 */
    @JsonProperty(value = "bServiceFlowDetailList")
    private List<BServiceFlowDetail> bServiceFlowDetailList;
}
