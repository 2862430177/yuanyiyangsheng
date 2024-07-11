package com.hewei.operatemanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 营业额统计结果vo（按日期）
 * @Author hewei
 * @Date 2024/6/11 14:10
 */
@Data
public class TurnoverStatisticsForDateResultVo implements Serializable {
    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 项目金额(当日)
     */
    private BigDecimal itemAmount;
    /**
     * 产品金额(当日)
     */
    private BigDecimal productAmount;
    /**
     * 总金额(当日)
     */
    private BigDecimal serviceCharge;
}
