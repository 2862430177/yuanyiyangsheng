package com.hewei.member.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @Description 会员信息查询条件
 * @Author hewei
 * @Date 2024/7/9 11:28
 */
@Data
public class BCustomerQueryVo {
    private String phonenumber;
    /**
     * 前台传递的参数
     */
    //性别
    private Integer sex;
    //会员余额
    private BigDecimal remainAmount;
    //上次到店消费间隔（天）
    private Integer lastCostTimeGap;
    //json串，demo:
    // fieldCompare:{
    //          sex: "=", //性别
    //          remainAmount:">=",//会员卡余额
    //          lastCostTimeGap:">=",//上次到店消费间隔
    //        }
    private Map<String,Object> fieldCompareMap;


    /**
     * 后台转换后的参数
     */
    //上次消费时间=当前时间-上次到店消费间隔
    private Date lastCostDateTime;
}
