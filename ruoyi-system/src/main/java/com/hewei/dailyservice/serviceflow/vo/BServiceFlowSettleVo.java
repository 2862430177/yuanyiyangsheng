package com.hewei.dailyservice.serviceflow.vo;

import com.hewei.dailyservice.serviceflow.domain.BServiceFlow;
import lombok.Data;

/**
 * @Description 确认结算，页面提交信息vo
 * @Author hewei
 * @Date 2024/6/6 23:45
 */
@Data
public class BServiceFlowSettleVo extends BServiceFlow {

    //服务员工姓名
    private String employeeName;
    //服务设备名
    private String equipmentName;
    //会员名称
    private String customerName;
    //会员手机号
    private String phonenumber;

}
