package com.hewei.employee.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 员工信息管理对象 b_employee
 * 
 * @author hewei
 * @date 2024-06-04
 */
@Data
public class BEmployee extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 员工id */
    private Long id;

    /** 员工姓名 */
    @Excel(name = "员工姓名")
    private String employeeName;

    /** 员工昵称 */
    @Excel(name = "员工昵称")
    private String nickName;

    /** 工号(唯一) */
    @Excel(name = "工号(唯一)")
    private String jobid;

    /** 员工技能等级（数据字典，0无级别 1初级 2中级3高级） */
    @Excel(name = "员工技能等级", readConverterExp = "数=据字典，0无级别,1=初级,2=中级3高级")
    private String skillLevel;

    /** 员工手机号码 */
    @Excel(name = "员工手机号码")
    private String phonenumber;

    /** 员工性别（0男 1女 2未知） */
    @Excel(name = "员工性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 员工工作状态(0空闲中,1工作中 2休假中) */
    @Excel(name = "员工工作状态(0空闲中,1工作中 2休假中)")
    private String workingStatus;

    /** 员工在职状态（0在职 1离职） */
    @Excel(name = "员工在职状态", readConverterExp = "0=在职,1=离职")
    private String employmentStatus;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;
}
