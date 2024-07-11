package com.hewei.operatemanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 项目维度统计-结果vo
 * @Author hewei
 * @Date 2024/6/27 15:22
 */
@Data
public class ItemsDimensionalStatisticsResultVo {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date month;
    private String itemName;
    private BigDecimal sales;
}
