package com.hewei.shop.equipment.mapper;

import java.util.List;
import com.hewei.shop.equipment.domain.BRoomEquipment;
import org.apache.ibatis.annotations.Param;

/**
 * 房间设备管理Mapper接口
 * 
 * @author hewei
 * @date 2024-06-04
 */
public interface BRoomEquipmentMapper 
{
    /**
     * 查询房间设备管理
     * 
     * @param id 房间设备管理主键
     * @return 房间设备管理
     */
    public BRoomEquipment selectBRoomEquipmentById(Long id);

    /**
     * 查询房间设备管理列表
     * 
     * @param bRoomEquipment 房间设备管理
     * @return 房间设备管理集合
     */
    public List<BRoomEquipment> selectBRoomEquipmentList(BRoomEquipment bRoomEquipment);
    List<BRoomEquipment> selectBRoomEquipmentByIds(@Param("emquipmentIds") List<Long> emquipmentIds);
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
     * 删除房间设备管理
     * 
     * @param id 房间设备管理主键
     * @return 结果
     */
    public int deleteBRoomEquipmentById(Long id);

    /**
     * 批量删除房间设备管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBRoomEquipmentByIds(Long[] ids);
}
