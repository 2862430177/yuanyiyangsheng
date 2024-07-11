package com.hewei.shop.serviceproducts.mapper;

import java.util.List;
import com.hewei.shop.serviceproducts.domain.BServiceProducts;

/**
 * 服务产品管理Mapper接口
 * 
 * @author hewei
 * @date 2024-06-04
 */
public interface BServiceProductsMapper 
{
    /**
     * 查询服务产品管理
     * 
     * @param id 服务产品管理主键
     * @return 服务产品管理
     */
    public BServiceProducts selectBServiceProductsById(Long id);

    /**
     * 查询服务产品管理列表
     * 
     * @param bServiceProducts 服务产品管理
     * @return 服务产品管理集合
     */
    public List<BServiceProducts> selectBServiceProductsList(BServiceProducts bServiceProducts);

    /**
     * 新增服务产品管理
     * 
     * @param bServiceProducts 服务产品管理
     * @return 结果
     */
    public int insertBServiceProducts(BServiceProducts bServiceProducts);

    /**
     * 修改服务产品管理
     * 
     * @param bServiceProducts 服务产品管理
     * @return 结果
     */
    public int updateBServiceProducts(BServiceProducts bServiceProducts);

    /**
     * 删除服务产品管理
     * 
     * @param id 服务产品管理主键
     * @return 结果
     */
    public int deleteBServiceProductsById(Long id);

    /**
     * 批量删除服务产品管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBServiceProductsByIds(Long[] ids);
}
