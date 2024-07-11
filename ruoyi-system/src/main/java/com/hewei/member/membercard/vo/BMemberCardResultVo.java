package com.hewei.member.membercard.vo;

import com.hewei.member.membercard.domain.BMemberCard;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 页面返回vo
 * @Author hewei
 * @Date 2024/6/5 16:29
 */
@Data
public class BMemberCardResultVo extends BMemberCard {
    private String phonenumber;
    private Long recommendEmployeeId;
    private String recommendEmployeeName;
}
