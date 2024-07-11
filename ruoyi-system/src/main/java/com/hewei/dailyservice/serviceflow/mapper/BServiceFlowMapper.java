package com.hewei.dailyservice.serviceflow.mapper;

import java.util.List;
import com.hewei.dailyservice.serviceflow.domain.BServiceFlow;
import com.hewei.dailyservice.serviceflow.domain.BServiceFlowDetail;

/**
 * 服务流水,一次服务一条流水记录Mapper接口
 * 
 * @author hewei
 * @date 2024-06-05
 */
public interface BServiceFlowMapper 
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

    /**
     * 新增服务流水,一次服务一条流水记录
     * 
     * @param bServiceFlow 服务流水,一次服务一条流水记录
     * @return 结果
     */
    public int insertBServiceFlow(BServiceFlow bServiceFlow);

    /**
     * 修改服务流水,一次服务一条流水记录
     * 
     * @param bServiceFlow 服务流水,一次服务一条流水记录
     * @return 结果
     */
    public int updateBServiceFlow(BServiceFlow bServiceFlow);

    /**
     * 删除服务流水,一次服务一条流水记录
     * 
     * @param id 服务流水,一次服务一条流水记录主键
     * @return 结果
     */
    public int deleteBServiceFlowById(Long id);

    /**
     * 批量删除服务流水,一次服务一条流水记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBServiceFlowByIds(Long[] ids);

    /**
     * 批量删除服务流水,一次服务一条流水记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBServiceFlowDetailByFlowIds(Long[] ids);
    
    /**
     * 批量新增服务流水,一次服务一条流水记录
     * 
     * @param bServiceFlowDetailList 服务流水,一次服务一条流水记录列表
     * @return 结果
     */
    public int batchBServiceFlowDetail(List<BServiceFlowDetail> bServiceFlowDetailList);
    

    /**
     * 通过服务流水,一次服务一条流水记录主键删除服务流水,一次服务一条流水记录信息
     * 
     * @param id 服务流水,一次服务一条流水记录ID
     * @return 结果
     */
    public int deleteBServiceFlowDetailByFlowId(Long id);
}
