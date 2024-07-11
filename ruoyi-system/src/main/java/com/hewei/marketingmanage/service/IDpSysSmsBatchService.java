package com.hewei.marketingmanage.service;

import java.util.List;
import com.hewei.marketingmanage.domain.DpSysSmsBatch;

/**
 * 短信消息批量发送定时执行状态Service接口
 * 
 * @author hewei
 * @date 2024-07-08
 */
public interface IDpSysSmsBatchService 
{
    /**
     * 查询短信消息批量发送定时执行状态
     * 
     * @param id 短信消息批量发送定时执行状态主键
     * @return 短信消息批量发送定时执行状态
     */
    public DpSysSmsBatch selectDpSysSmsBatchById(Long id);

    /**
     * 查询短信消息批量发送定时执行状态列表
     * 
     * @param dpSysSmsBatch 短信消息批量发送定时执行状态
     * @return 短信消息批量发送定时执行状态集合
     */
    public List<DpSysSmsBatch> selectDpSysSmsBatchList(DpSysSmsBatch dpSysSmsBatch);

    /**
     * 新增短信消息批量发送定时执行状态
     * 
     * @param dpSysSmsBatch 短信消息批量发送定时执行状态
     * @return 结果
     */
    public int insertDpSysSmsBatch(DpSysSmsBatch dpSysSmsBatch);

    /**
     * 修改短信消息批量发送定时执行状态
     * 
     * @param dpSysSmsBatch 短信消息批量发送定时执行状态
     * @return 结果
     */
    public int updateDpSysSmsBatch(DpSysSmsBatch dpSysSmsBatch);

    /**
     * 批量删除短信消息批量发送定时执行状态
     * 
     * @param ids 需要删除的短信消息批量发送定时执行状态主键集合
     * @return 结果
     */
    public int deleteDpSysSmsBatchByIds(Long[] ids);

    /**
     * 删除短信消息批量发送定时执行状态信息
     * 
     * @param id 短信消息批量发送定时执行状态主键
     * @return 结果
     */
    public int deleteDpSysSmsBatchById(Long id);

    DpSysSmsBatch getEarlyBatchByExcuteDateTime(String sendStatus);
}
