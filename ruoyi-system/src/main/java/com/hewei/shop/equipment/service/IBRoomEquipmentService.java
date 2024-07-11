package com.hewei.shop.equipment.service;

import java.util.List;
import com.hewei.shop.equipment.domain.BRoomEquipment;

/**
 * 房间设备管理Service接口
 * 
 * @author hewei
 * @date 2024-06-04
 */
public interface IBRoomEquipmentService 
{
    /**
     * 查询房间设备管理
     * 
     * @param id 房间设备管理主键
     * @return 房间设备管理
     */
    BRoomEquipment selectBRoomEquipmentById(Long id);

    /**
     * 查询房间设备管理列表
     * 
     * @param bRoomEquipment 房间设备管理
     * @return 房间设备管理集合
     */
    List<BRoomEquipment> selectBRoomEquipmentList(BRoomEquipment bRoomEquipment);
    List<BRoomEquipment> selectBRoomEquipmentByIds(List<Long> emquipmentIds);
    /**
     * 新增房间设备管理
     * 
     * @param bRoomEquipment 房间设备管理
     * @return 结果
     */
    public int insertBRoomEquipment(BRoomEquipment bRoomEquipment);

    /**
     * 修改房间设备管理
     * 
     * @param bRoomEquipment 房间设备管理
     * @return 结果
     */
    public int updateBRoomEquipment(BRoomEquipment bRoomEquipment);

    /**
     * 批量删除房间设备管理
     * 
     * @param ids 需要删除的房间设备管理主键集合
     * @return 结果
     */
    public int deleteBRoomEquipmentByIds(Long[] ids);

    /**
     * 删除房间设备管理信息
     * 
     * @param id 房间设备管理主键
     * @return 结果
     */
    public int deleteBRoomEquipmentById(Long id);


}
