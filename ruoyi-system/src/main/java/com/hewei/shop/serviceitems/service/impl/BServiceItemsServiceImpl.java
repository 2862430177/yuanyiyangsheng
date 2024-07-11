package com.hewei.shop.serviceitems.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hewei.shop.serviceitems.mapper.BServiceItemsMapper;
import com.hewei.shop.serviceitems.domain.BServiceItems;
import com.hewei.shop.serviceitems.service.IBServiceItemsService;

/**
 * 服务项目管理Service业务层处理
 * 
 * @author hewei
 * @date 2024-06-04
 */
@Service
public class BServiceItemsServiceImpl implements IBServiceItemsService 
{
    @Autowired
    private BServiceItemsMapper bServiceItemsMapper;

    /**
     * 查询服务项目管理
     * 
     * @param id 服务项目管理主键
     * @return 服务项目管理
     */
    @Override
    public BServiceItems selectBServiceItemsById(Long id)
    {
        return bServiceItemsMapper.selectBServiceItemsById(id);
    }

    /**
     * 查询服务项目管理列表
     * 
     * @param bServiceItems 服务项目管理
     * @return 服务项目管理
     */
    @Override
    public List<BServiceItems> selectBServiceItemsList(BServiceItems bServiceItems)
    {
        return bServiceItemsMapper.selectBServiceItemsList(bServiceItems);
    }

    /**
     * 新增服务项目管理
     * 
     * @param bServiceItems 服务项目管理
     * @return 结果
     */
    @Override
    public int insertBServiceItems(BServiceItems bServiceItems)
    {
        bServiceItems.setCreateTime(DateUtils.getNowDate());
        return bServiceItemsMapper.insertBServiceItems(bServiceItems);
    }

    /**
     * 修改服务项目管理
     * 
     * @param bServiceItems 服务项目管理
     * @return 结果
     */
    @Override
    public int updateBServiceItems(BServiceItems bServiceItems)
    {
        bServiceItems.setUpdateTime(DateUtils.getNowDate());
        return bServiceItemsMapper.updateBServiceItems(bServiceItems);
    }

    /**
     * 批量删除服务项目管理
     * 
     * @param ids 需要删除的服务项目管理主键
     * @return 结果
     */
    @Override
    public int deleteBServiceItemsByIds(Long[] ids)
    {
        return bServiceItemsMapper.deleteBServiceItemsByIds(ids);
    }

    /**
     * 删除服务项目管理信息
     * 
     * @param id 服务项目管理主键
     * @return 结果
     */
    @Override
    public int deleteBServiceItemsById(Long id)
    {
        return bServiceItemsMapper.deleteBServiceItemsById(id);
    }
}
