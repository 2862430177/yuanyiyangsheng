package com.hewei.shop.serviceitems.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 服务项目管理对象 b_service_items
 * 
 * @author hewei
 * @date 2024-06-04
 */
@Data
public class BServiceItems extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 服务项目id */
    private Long id;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String itemName;

    /** 项目时长（分钟） */
    @Excel(name = "项目时长", readConverterExp = "分=钟")
    private Long itemDuration;

    /** 项目收费标准(每次收费) */
    @Excel(name = "项目收费标准(每次收费)")
    private BigDecimal itemChargeStandard;

    /** 项目内容(包含) */
    @Excel(name = "项目内容(包含)")
    private String itemContent;

    /** 项目状态（0正常 1停用） */
    @Excel(name = "项目状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 项目提成（固定金额） */
    @Excel(name = "项目提成", readConverterExp = "固定金额")
    private BigDecimal commissionFixedAmount;

    /** 项目提成（固定比例） */
    @Excel(name = "项目提成", readConverterExp = "固定比例")
    private BigDecimal commissionFixedProportion;
}
