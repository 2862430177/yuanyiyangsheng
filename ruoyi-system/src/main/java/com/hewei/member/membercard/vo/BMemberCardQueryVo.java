package com.hewei.member.membercard.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 会员卡页面查询条件
 * @Author hewei
 * @Date 2024/6/5 15:42
 */
@Data
public class BMemberCardQueryVo implements Serializable {

    //会员手机号
    private String phonenumber;
    //会员id
    private Long customerId;
    //卡号
    private String cardno;
    //卡密
    private String cardSecret;
    //如果查不到会员卡是否创建
    private boolean ifCreateCard;
}
