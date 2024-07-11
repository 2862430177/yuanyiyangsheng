package com.hewei.operatemanage.mapper;

import com.hewei.operatemanage.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @Description 运营管理数据库mapper
 * @Author hewei
 * @Date 2024/6/11 15:08
 */
public interface OperatemanageMapper {

    List<TurnoverStatisticsForDateResultVo> selectTurnoverStatisticsList(TurnoverStatisticsQueryVo queryVo);

    List<TurnoverStatisticsForDateResultVo> selectTurnoverStatisticsByItemList(TurnoverStatisticsQueryVo queryVo);

    List<TurnoverStatisticsForDateResultVo> selectTurnoverStatisticsByProductList(TurnoverStatisticsQueryVo queryVo);

    List<EmployeePerformanceindicatorResultVo> selectActualServicecharge(EmployeePerformanceStatisticsQueryVo queryVo);

    List<Map<String, Object>> selectItemProductAmountByStore(TurnoverStatisticsQueryVo queryVo);

    List<Map<String, Object>> selectItemProductAmount(EmployeePerformanceStatisticsQueryVo queryVo);

    List<Map<String, Object>> selectRechargeLog(EmployeePerformanceStatisticsQueryVo queryVo);

    List<Map<String, Object>> selectMemberCardFirstAmnout(EmployeePerformanceStatisticsQueryVo queryVo);

    List<Map<String, Object>> selectItemsDimensionalStatisticsListByDate(DateQueryVo dateQueryVo);

    List<Map<String, Object>> selectProductsDimensionalStatisticsListByDate(DateQueryVo dateQueryVo);

    List<Map<String, Object>> selectItemsDimensionalStatisticsList(DateQueryVo dateQueryVo);

    List<Map<String, Object>> selectProductsDimensionalStatisticsList(DateQueryVo dateQueryVo);


}
