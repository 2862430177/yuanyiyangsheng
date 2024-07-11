package com.hewei.shop.equipment.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 房间设备管理对象 b_room_equipment
 * 
 * @author hewei
 * @date 2024-06-04
 */
public class BRoomEquipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备id */
    private Long id;

    /** 设备自定义编号 */
    @Excel(name = "设备自定义编号")
    private String equipmentNo;

    /** 设备类型（数据字典code，1床位 2足疗仪等） */
    @Excel(name = "设备类型", readConverterExp = "数=据字典code，1床位,2=足疗仪等")
    private String status;

    /** 设备描述 */
    @Excel(name = "设备描述")
    private String equipmentDesc;

    /** 设备使用状态（0未使用 1使用中） */
    @Excel(name = "设备使用状态", readConverterExp = "0=未使用,1=使用中")
    private String useStatus;

    /** 开始使用时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始使用时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startUseTime;

    /** 结束使用时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束使用时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endUseTime;

    /** 持续使用时间（分钟）=结束使用时间-开始使用时间 */
    @Excel(name = "持续使用时间", readConverterExp = "分=钟")
    private Long continueUsageTime;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setEquipmentNo(String equipmentNo) 
    {
        this.equipmentNo = equipmentNo;
    }

    public String getEquipmentNo() 
    {
        return equipmentNo;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setEquipmentDesc(String equipmentDesc) 
    {
        this.equipmentDesc = equipmentDesc;
    }

    public String getEquipmentDesc() 
    {
        return equipmentDesc;
    }
    public void setUseStatus(String useStatus) 
    {
        this.useStatus = useStatus;
    }

    public String getUseStatus() 
    {
        return useStatus;
    }
    public void setStartUseTime(Date startUseTime) 
    {
        this.startUseTime = startUseTime;
    }

    public Date getStartUseTime() 
    {
        return startUseTime;
    }
    public void setEndUseTime(Date endUseTime) 
    {
        this.endUseTime = endUseTime;
    }

    public Date getEndUseTime() 
    {
        return endUseTime;
    }
    public void setContinueUsageTime(Long continueUsageTime) 
    {
        this.continueUsageTime = continueUsageTime;
    }

    public Long getContinueUsageTime() 
    {
        return continueUsageTime;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("equipmentNo", getEquipmentNo())
            .append("status", getStatus())
            .append("equipmentDesc", getEquipmentDesc())
            .append("useStatus", getUseStatus())
            .append("startUseTime", getStartUseTime())
            .append("endUseTime", getEndUseTime())
            .append("continueUsageTime", getContinueUsageTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
