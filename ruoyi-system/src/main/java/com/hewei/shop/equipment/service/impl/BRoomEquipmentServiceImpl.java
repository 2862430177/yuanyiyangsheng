package com.hewei.shop.equipment.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hewei.shop.equipment.mapper.BRoomEquipmentMapper;
import com.hewei.shop.equipment.domain.BRoomEquipment;
import com.hewei.shop.equipment.service.IBRoomEquipmentService;

/**
 * 房间设备管理Service业务层处理
 * 
 * @author hewei
 * @date 2024-06-04
 */
@Service
public class BRoomEquipmentServiceImpl implements IBRoomEquipmentService 
{
    @Autowired
    private BRoomEquipmentMapper bRoomEquipmentMapper;

    /**
     * 查询房间设备管理
     * 
     * @param id 房间设备管理主键
     * @return 房间设备管理
     */
    @Override
    public BRoomEquipment selectBRoomEquipmentById(Long id)
    {
        return bRoomEquipmentMapper.selectBRoomEquipmentById(id);
    }

    /**
     * 查询房间设备管理列表
     * 
     * @param bRoomEquipment 房间设备管理
     * @return 房间设备管理
     */
    @Override
    public List<BRoomEquipment> selectBRoomEquipmentList(BRoomEquipment bRoomEquipment)
    {
        return bRoomEquipmentMapper.selectBRoomEquipmentList(bRoomEquipment);
    }

    @Override
    public List<BRoomEquipment> selectBRoomEquipmentByIds(List<Long> emquipmentIds) {
        if(CollectionUtils.isEmpty(emquipmentIds)){
            return new ArrayList<>();
        }
        return bRoomEquipmentMapper.selectBRoomEquipmentByIds(emquipmentIds);
    }

    /**
     * 新增房间设备管理
     * 
     * @param bRoomEquipment 房间设备管理
     * @return 结果
     */
    @Override
    public int insertBRoomEquipment(BRoomEquipment bRoomEquipment)
    {
        bRoomEquipment.setCreateTime(DateUtils.getNowDate());
        return bRoomEquipmentMapper.insertBRoomEquipment(bRoomEquipment);
    }

    /**
     * 修改房间设备管理
     * 
     * @param bRoomEquipment 房间设备管理
     * @return 结果
     */
    @Override
    public int updateBRoomEquipment(BRoomEquipment bRoomEquipment)
    {
        bRoomEquipment.setUpdateTime(DateUtils.getNowDate());
        return bRoomEquipmentMapper.updateBRoomEquipment(bRoomEquipment);
    }

    /**
     * 批量删除房间设备管理
     * 
     * @param ids 需要删除的房间设备管理主键
     * @return 结果
     */
    @Override
    public int deleteBRoomEquipmentByIds(Long[] ids)
    {
        return bRoomEquipmentMapper.deleteBRoomEquipmentByIds(ids);
    }

    /**
     * 删除房间设备管理信息
     * 
     * @param id 房间设备管理主键
     * @return 结果
     */
    @Override
    public int deleteBRoomEquipmentById(Long id)
    {
        return bRoomEquipmentMapper.deleteBRoomEquipmentById(id);
    }
}
