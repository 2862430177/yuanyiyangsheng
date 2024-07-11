package com.hewei.dailyservice.serviceflow.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 服务流水,一次服务一条流水记录对象 b_service_flow_detail
 * 
 * @author hewei
 * @date 2024-06-05
 */
public class BServiceFlowDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 服务流水明细id */
    private Long id;

    /** 对应服务流水id */
    @Excel(name = "对应服务流水id")
    private Long flowId;

    /** 服务项类型(0项目1产品) */
    @Excel(name = "服务项类型(0项目1产品)")
    private String detailType;

    /** 服务项对应主键id(b_service_items主键、b_service_products主键) */
    @Excel(name = "服务项对应主键id(b_service_items主键、b_service_products主键)")
    private Long detailPkId;

    /** 服务项内容简介 */
    @Excel(name = "服务项内容简介")
    private String detailName;

    /** 服务项数量 */
    @Excel(name = "服务项数量")
    private Long detailNum;

    /** 服务项单价 */
    @Excel(name = "服务项单价")
    private BigDecimal detailUnitPrice;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFlowId(Long flowId) 
    {
        this.flowId = flowId;
    }

    public Long getFlowId() 
    {
        return flowId;
    }
    public void setDetailType(String detailType) 
    {
        this.detailType = detailType;
    }

    public String getDetailType() 
    {
        return detailType;
    }
    public void setDetailPkId(Long detailPkId) 
    {
        this.detailPkId = detailPkId;
    }

    public Long getDetailPkId() 
    {
        return detailPkId;
    }
    public void setDetailName(String detailName) 
    {
        this.detailName = detailName;
    }

    public String getDetailName() 
    {
        return detailName;
    }
    public void setDetailNum(Long detailNum) 
    {
        this.detailNum = detailNum;
    }

    public Long getDetailNum() 
    {
        return detailNum;
    }
    public void setDetailUnitPrice(BigDecimal detailUnitPrice) 
    {
        this.detailUnitPrice = detailUnitPrice;
    }

    public BigDecimal getDetailUnitPrice() 
    {
        return detailUnitPrice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("flowId", getFlowId())
            .append("detailType", getDetailType())
            .append("detailPkId", getDetailPkId())
            .append("detailName", getDetailName())
            .append("detailNum", getDetailNum())
            .append("detailUnitPrice", getDetailUnitPrice())
            .toString();
    }
}
