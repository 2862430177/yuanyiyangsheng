package com.hewei.web.controller.operatemanage;

import com.hewei.operatemanage.service.IOperatemanageService;
import com.hewei.operatemanage.vo.DateQueryVo;
import com.hewei.operatemanage.vo.EmployeePerformanceStatisticsQueryVo;
import com.hewei.operatemanage.vo.TurnoverStatisticsQueryVo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 运营管理
 * 
 * @author hewei
 */
@RestController
@RequestMapping("/operatemanage")
public class OperatemanageController extends BaseController
{
    @Autowired
    private IOperatemanageService operatemanageService;
    /**
     * 营业额统计列表
     */
    @GetMapping(value = "turnoverStatistics/list")
    public AjaxResult getTurnoverStatisticsList(TurnoverStatisticsQueryVo turnoverStatisticsQueryVo)
    {
        return AjaxResult.success(operatemanageService.getTurnoverStatisticsList(turnoverStatisticsQueryVo));
    }

    /**
     * 员工业绩统计列表
     */
    @GetMapping(value = "employeePerformanceStatistics/list")
    public AjaxResult getEmployeePerformanceStatisticsList(EmployeePerformanceStatisticsQueryVo employeePerformanceStatisticsQueryVo)
    {
        return AjaxResult.success(operatemanageService.getEmployeePerformanceStatisticsList(employeePerformanceStatisticsQueryVo));
    }

    /**
     * 项目维度统计柱状图
     */
    @GetMapping(value = "itemsDimensionalStatistics/list")
    public AjaxResult getItemsDimensionalStatisticsList(DateQueryVo dateQueryVo)
    {
        return AjaxResult.success(operatemanageService.getItemsDimensionalStatisticsList(dateQueryVo));
    }

    /**
     * 产品维度统计柱状图
     */
    @GetMapping(value = "productsDimensionalStatistics/list")
    public AjaxResult getProductsDimensionalStatisticsList(DateQueryVo dateQueryVo)
    {
        return AjaxResult.success(operatemanageService.getProductsDimensionalStatisticsList(dateQueryVo));
    }


}
