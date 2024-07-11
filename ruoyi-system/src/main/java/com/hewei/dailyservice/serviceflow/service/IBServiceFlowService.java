package com.hewei.dailyservice.serviceflow.service;

import java.util.List;
import com.hewei.dailyservice.serviceflow.domain.BServiceFlow;
import com.hewei.dailyservice.serviceflow.vo.BServiceFlowQueryVo;
import com.hewei.dailyservice.serviceflow.vo.BServiceFlowResultVo;
import com.hewei.dailyservice.serviceflow.vo.BServiceFlowSettleVo;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * 服务流水,一次服务一条流水记录Service接口
 * 
 * @author hewei
 * @date 2024-06-05
 */
public interface IBServiceFlowService 
{
    /**
     * 查询服务流水,一次服务一条流水记录
     * 
     * @param id 服务流水,一次服务一条流水记录主键
     * @return 服务流水,一次服务一条流水记录
     */
    public BServiceFlow selectBServiceFlowById(Long id);

    /**
     * 查询服务流水,一次服务一条流水记录列表
     * 
     * @param bServiceFlow 服务流水,一次服务一条流水记录
     * @return 服务流水,一次服务一条流水记录集合
     */
    public List<BServiceFlow> selectBServiceFlowList(BServiceFlow bServiceFlow);
    List<BServiceFlowResultVo> selectBServiceFlowList(BServiceFlowQueryVo serviceFlowQueryVo);
    /**
     * 新增服务流水,一次服务一条流水记录
     * 
     * @param bServiceFlow 服务流水,一次服务一条流水记录
     * @return 结果
     */
    public int insertBServiceFlow(BServiceFlow bServiceFlow);

    AjaxResult settle(BServiceFlowSettleVo serviceFlowSettleVo);
    AjaxResult reSettle(BServiceFlowSettleVo serviceFlowSettleVo);

    /**
     * 修改服务流水,一次服务一条流水记录
     * 
     * @param bServiceFlow 服务流水,一次服务一条流水记录
     * @return 结果
     */
    public int updateBServiceFlow(BServiceFlow bServiceFlow);

    /**
     * 批量删除服务流水,一次服务一条流水记录
     * 
     * @param ids 需要删除的服务流水,一次服务一条流水记录主键集合
     * @return 结果
     */
    public int deleteBServiceFlowByIds(Long[] ids);

    /**
     * 删除服务流水,一次服务一条流水记录信息
     * 
     * @param id 服务流水,一次服务一条流水记录主键
     * @return 结果
     */
    public int deleteBServiceFlowById(Long id);
}
