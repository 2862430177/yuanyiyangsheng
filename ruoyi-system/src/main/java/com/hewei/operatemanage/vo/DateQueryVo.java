package com.hewei.operatemanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 日期查询条件
 * @Author hewei
 * @Date 2024/6/27 15:19
 */
@Data
public class DateQueryVo implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
}
