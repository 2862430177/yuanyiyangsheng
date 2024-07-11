package com.hewei.common.constant;

/**
 * @Description 数据字典常量
 * @Author hewei
 * @Date 2024/6/7 9:29
 */
public class DictConstants {
    /**
     * 结算方式 0：现金，1：会员卡
     */
    public static final String settlementType_cash = "0";
    public static final String settlementType_membercard = "1";
    /**
     * 结算状态 服务状态（0服务中 1已取消 2、已结算 3、已结束(非正常结算)） 4、已重新结算
     */
    public static final String settlementStatus_inprocess="0";
    public static final String settlementStatus_canceled="1";
    public static final String settlementStatus_settled="2";
    public static final String settlementStatus_ended="3";
    public static final String settlementStatus_hasReSettled="4";
    /**
     * 设备使用状态（0未使用 1使用中）
     */
    public static final String equipmentUseStatus_unuse = "0";
    public static final String equipmentUseStatus_using = "1";
    /**
     * 员工工作状态(0空闲中,1工作中 2休假中)
     */
    public static final String workingStatus_free = "0";
    public static final String workingStatus_working = "1";
    public static final String workingStatus_holiday = "2";

    /**
     * 消息发送状态(0未开始，1发送中，2发送成功，3发送失败，4部分成功)
     */
    public static final String messageSendStatus_unbegin = "0";
    public static final String messageSendStatus_sending = "1";
    public static final String messageSendStatus_success = "2";
    public static final String messageSendStatus_failure = "3";
    public static final String messageSendStatus_partSuccess = "4";
}
