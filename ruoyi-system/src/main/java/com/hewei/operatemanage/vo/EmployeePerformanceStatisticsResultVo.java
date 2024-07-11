package com.hewei.operatemanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 员工业绩统计返回结果vo
 * @Author hewei
 * @Date 2024/6/12 14:33
 */
@Data
public class EmployeePerformanceStatisticsResultVo {

    //员工id
    private Long employeeId;
    /** 员工姓名 */
    private String employeeName;
    //员工绩效合计=SUM(提成总计)
    private BigDecimal totalPerformanceAmount;
    //员工绩效指标，按日期<日期,绩效指标>
    Map<String,EmployeePerformanceindicatorResultVo> performanceindicatorResultVoMap;
}
