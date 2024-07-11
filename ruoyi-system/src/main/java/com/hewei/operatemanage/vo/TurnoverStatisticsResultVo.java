package com.hewei.operatemanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Description 营业额统计查询条件vo
 * @Author hewei
 * @Date 2024/6/11 14:10
 */
@Data
public class TurnoverStatisticsResultVo implements Serializable {

    /**
     * 项目金额汇总
     */
    private String totalItemAmount;
    /**
     * 产品金额汇总
     */
    private String totalProductAmount;
    /**
     * 总金额汇总
     */
    private String totalServiceCharge;

    private Map<String,TurnoverStatisticsForDateResultVo> dateResultVoMap;
}
