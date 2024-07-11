package com.hewei.shop.serviceproducts.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hewei.shop.serviceproducts.mapper.BServiceProductsMapper;
import com.hewei.shop.serviceproducts.domain.BServiceProducts;
import com.hewei.shop.serviceproducts.service.IBServiceProductsService;

/**
 * 服务产品管理Service业务层处理
 * 
 * @author hewei
 * @date 2024-06-04
 */
@Service
public class BServiceProductsServiceImpl implements IBServiceProductsService 
{
    @Autowired
    private BServiceProductsMapper bServiceProductsMapper;

    /**
     * 查询服务产品管理
     * 
     * @param id 服务产品管理主键
     * @return 服务产品管理
     */
    @Override
    public BServiceProducts selectBServiceProductsById(Long id)
    {
        return bServiceProductsMapper.selectBServiceProductsById(id);
    }

    /**
     * 查询服务产品管理列表
     * 
     * @param bServiceProducts 服务产品管理
     * @return 服务产品管理
     */
    @Override
    public List<BServiceProducts> selectBServiceProductsList(BServiceProducts bServiceProducts)
    {
        return bServiceProductsMapper.selectBServiceProductsList(bServiceProducts);
    }

    /**
     * 新增服务产品管理
     * 
     * @param bServiceProducts 服务产品管理
     * @return 结果
     */
    @Override
    public int insertBServiceProducts(BServiceProducts bServiceProducts)
    {
        bServiceProducts.setCreateTime(DateUtils.getNowDate());
        return bServiceProductsMapper.insertBServiceProducts(bServiceProducts);
    }

    /**
     * 修改服务产品管理
     * 
     * @param bServiceProducts 服务产品管理
     * @return 结果
     */
    @Override
    public int updateBServiceProducts(BServiceProducts bServiceProducts)
    {
        bServiceProducts.setUpdateTime(DateUtils.getNowDate());
        return bServiceProductsMapper.updateBServiceProducts(bServiceProducts);
    }

    /**
     * 批量删除服务产品管理
     * 
     * @param ids 需要删除的服务产品管理主键
     * @return 结果
     */
    @Override
    public int deleteBServiceProductsByIds(Long[] ids)
    {
        return bServiceProductsMapper.deleteBServiceProductsByIds(ids);
    }

    /**
     * 删除服务产品管理信息
     * 
     * @param id 服务产品管理主键
     * @return 结果
     */
    @Override
    public int deleteBServiceProductsById(Long id)
    {
        return bServiceProductsMapper.deleteBServiceProductsById(id);
    }
}
