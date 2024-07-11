package com.hewei.marketingmanage.service.impl;

import java.util.Comparator;
import java.util.List;

import com.hewei.common.constant.DictConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hewei.marketingmanage.mapper.DpSysSmsBatchMapper;
import com.hewei.marketingmanage.domain.DpSysSmsBatch;
import com.hewei.marketingmanage.service.IDpSysSmsBatchService;

/**
 * 短信消息批量发送定时执行状态Service业务层处理
 * 
 * @author hewei
 * @date 2024-07-08
 */
@Slf4j
@Service
public class DpSysSmsBatchServiceImpl implements IDpSysSmsBatchService 
{
    @Autowired
    private DpSysSmsBatchMapper dpSysSmsBatchMapper;

    /**
     * 查询短信消息批量发送定时执行状态
     * 
     * @param id 短信消息批量发送定时执行状态主键
     * @return 短信消息批量发送定时执行状态
     */
    @Override
    public DpSysSmsBatch selectDpSysSmsBatchById(Long id)
    {
        return dpSysSmsBatchMapper.selectDpSysSmsBatchById(id);
    }

    /**
     * 查询短信消息批量发送定时执行状态列表
     * 
     * @param dpSysSmsBatch 短信消息批量发送定时执行状态
     * @return 短信消息批量发送定时执行状态
     */
    @Override
    public List<DpSysSmsBatch> selectDpSysSmsBatchList(DpSysSmsBatch dpSysSmsBatch)
    {
        return dpSysSmsBatchMapper.selectDpSysSmsBatchList(dpSysSmsBatch);
    }

    /**
     * 新增短信消息批量发送定时执行状态
     * 
     * @param dpSysSmsBatch 短信消息批量发送定时执行状态
     * @return 结果
     */
    @Override
    public int insertDpSysSmsBatch(DpSysSmsBatch dpSysSmsBatch)
    {
        //初始化发送状态为未发送
        dpSysSmsBatch.setSendStatus(DictConstants.messageSendStatus_unbegin);
        return dpSysSmsBatchMapper.insertDpSysSmsBatch(dpSysSmsBatch);
    }

    /**
     * 修改短信消息批量发送定时执行状态
     * 
     * @param dpSysSmsBatch 短信消息批量发送定时执行状态
     * @return 结果
     */
    @Override
    public int updateDpSysSmsBatch(DpSysSmsBatch dpSysSmsBatch)
    {
        Long id = dpSysSmsBatch.getId();
        DpSysSmsBatch existBatch = this.selectDpSysSmsBatchById(id);
        if(existBatch != null){
            String sendStatus = existBatch.getSendStatus();
            if(!DictConstants.messageSendStatus_unbegin.equals(sendStatus)){
                log.error("非未发送状态，不做更新操作");
                return 0;
            }
        }
        return dpSysSmsBatchMapper.updateDpSysSmsBatch(dpSysSmsBatch);
    }

    /**
     * 批量删除短信消息批量发送定时执行状态
     * 
     * @param ids 需要删除的短信消息批量发送定时执行状态主键
     * @return 结果
     */
    @Override
    public int deleteDpSysSmsBatchByIds(Long[] ids)
    {
        return dpSysSmsBatchMapper.deleteDpSysSmsBatchByIds(ids);
    }

    /**
     * 删除短信消息批量发送定时执行状态信息
     * 
     * @param id 短信消息批量发送定时执行状态主键
     * @return 结果
     */
    @Override
    public int deleteDpSysSmsBatchById(Long id)
    {
        return dpSysSmsBatchMapper.deleteDpSysSmsBatchById(id);
    }

    @Override
    public DpSysSmsBatch getEarlyBatchByExcuteDateTime(String sendStatus) {
        DpSysSmsBatch queryCondition = new DpSysSmsBatch();
        queryCondition.setSendStatus(sendStatus);
        List<DpSysSmsBatch> allSmsBatch = this.selectDpSysSmsBatchList(queryCondition);
        if(allSmsBatch.isEmpty()){
            return null;
        }
        //正序
        allSmsBatch.sort(Comparator.comparing(DpSysSmsBatch::getPlanExcuteDatetime));
        //倒序
        allSmsBatch.sort(Comparator.comparing(DpSysSmsBatch::getPlanExcuteDatetime).reversed());
        return allSmsBatch.get(0);
    }
}
