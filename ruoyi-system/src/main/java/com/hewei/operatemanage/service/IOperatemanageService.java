package com.hewei.operatemanage.service;

import com.hewei.operatemanage.vo.*;

import java.util.List;

/**
 * @Description 运营管理service接口
 * @Author hewei
 * @Date 2024/6/11 14:07
 */
public interface IOperatemanageService {

    TurnoverStatisticsResultVo getTurnoverStatisticsList(TurnoverStatisticsQueryVo turnoverStatisticsQueryVo);

    List<EmployeePerformanceStatisticsResultVo> getEmployeePerformanceStatisticsList(EmployeePerformanceStatisticsQueryVo employeePerformanceStatisticsQueryVo);

    List<ItemsDimensionalStatisticsResultVo> getItemsDimensionalStatisticsList(DateQueryVo dateQueryVo);

    List<ProductsDimensionalStatisticsResultVo> getProductsDimensionalStatisticsList(DateQueryVo dateQueryVo);
}
