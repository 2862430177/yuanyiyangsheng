package com.hewei.dailyservice.serviceflow.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 服务流水页面查询条件
 * @Author hewei
 * @Date 2024/6/6 15:44
 */
@Data
public class BServiceFlowQueryVo implements Serializable {
    //服务员工姓名或工号
    private String nameOrWorkno;
    //会员手机号
    private String phonenumber;
    /** 服务状态（0服务中 1已取消 2、已结算 3、已结束(非正常结算)） */
    private String status;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
