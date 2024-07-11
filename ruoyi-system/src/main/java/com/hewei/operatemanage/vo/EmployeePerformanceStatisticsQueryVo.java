package com.hewei.operatemanage.vo;

import lombok.Data;

/**
 * @Description 员工业绩统计查询条件vo
 * @Author hewei
 * @Date 2024/6/12 14:33
 */
@Data
public class EmployeePerformanceStatisticsQueryVo extends DateQueryVo{

    //员工id
    private Long employeeId;
}
