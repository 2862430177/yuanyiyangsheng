package com.hewei.shop.serviceitems.mapper;

import java.util.List;
import com.hewei.shop.serviceitems.domain.BServiceItems;

/**
 * 服务项目管理Mapper接口
 * 
 * @author hewei
 * @date 2024-06-04
 */
public interface BServiceItemsMapper 
{
    /**
     * 查询服务项目管理
     * 
     * @param id 服务项目管理主键
     * @return 服务项目管理
     */
    public BServiceItems selectBServiceItemsById(Long id);

    /**
     * 查询服务项目管理列表
     * 
     * @param bServiceItems 服务项目管理
     * @return 服务项目管理集合
     */
    public List<BServiceItems> selectBServiceItemsList(BServiceItems bServiceItems);

    /**
     * 新增服务项目管理
     * 
     * @param bServiceItems 服务项目管理
     * @return 结果
     */
    public int insertBServiceItems(BServiceItems bServiceItems);

    /**
     * 修改服务项目管理
     * 
     * @param bServiceItems 服务项目管理
     * @return 结果
     */
    public int updateBServiceItems(BServiceItems bServiceItems);

    /**
     * 删除服务项目管理
     * 
     * @param id 服务项目管理主键
     * @return 结果
     */
    public int deleteBServiceItemsById(Long id);

    /**
     * 批量删除服务项目管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBServiceItemsByIds(Long[] ids);
}
