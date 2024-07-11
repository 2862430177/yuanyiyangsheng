package com.hewei.shop.serviceproducts.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 服务产品管理对象 b_service_products
 * 
 * @author hewei
 * @date 2024-06-04
 */
@Data
public class BServiceProducts extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 服务产品id */
    private Long id;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 产品收费标准(每次) */
    @Excel(name = "产品收费标准(每次)")
    private BigDecimal productChargeStandard;

    /** 产品描述 */
    @Excel(name = "产品描述")
    private String productDesc;

    /** 产品状态（0正常 1停用） */
    @Excel(name = "产品状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;
    /** 产品提成（固定金额） */
    @Excel(name = "产品提成", readConverterExp = "固=定金额")
    private BigDecimal commissionFixedAmount;

    /** 产品提成（固定比例） */
    @Excel(name = "产品提成", readConverterExp = "固=定比例")
    private BigDecimal commissionFixedProportion;

}
