package com.hewei.shop.equipment.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.hewei.shop.equipment.domain.BRoomEquipment;
import com.hewei.shop.equipment.service.IBRoomEquipmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 房间设备管理Controller
 * 
 * @author hewei
 * @date 2024-06-04
 */
@RestController
@RequestMapping("/shop/equipment")
public class BRoomEquipmentController extends BaseController
{
    @Autowired
    private IBRoomEquipmentService bRoomEquipmentService;

    /**
     * 查询房间设备管理列表
     */
    @PreAuthorize("@ss.hasPermi('shop:equipment:list')")
    @GetMapping("/list")
    public TableDataInfo list(BRoomEquipment bRoomEquipment)
    {
        startPage();
        List<BRoomEquipment> list = bRoomEquipmentService.selectBRoomEquipmentList(bRoomEquipment);
        return getDataTable(list);
    }

    /**
     * 导出房间设备管理列表
     */
    @PreAuthorize("@ss.hasPermi('shop:equipment:export')")
    @Log(title = "房间设备管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BRoomEquipment bRoomEquipment)
    {
        List<BRoomEquipment> list = bRoomEquipmentService.selectBRoomEquipmentList(bRoomEquipment);
        ExcelUtil<BRoomEquipment> util = new ExcelUtil<BRoomEquipment>(BRoomEquipment.class);
        util.exportExcel(response, list, "房间设备管理数据");
    }

    /**
     * 获取房间设备管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:equipment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bRoomEquipmentService.selectBRoomEquipmentById(id));
    }

    /**
     * 新增房间设备管理
     */
    @PreAuthorize("@ss.hasPermi('shop:equipment:add')")
    @Log(title = "房间设备管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BRoomEquipment bRoomEquipment)
    {
        return toAjax(bRoomEquipmentService.insertBRoomEquipment(bRoomEquipment));
    }

    /**
     * 修改房间设备管理
     */
    @PreAuthorize("@ss.hasPermi('shop:equipment:edit')")
    @Log(title = "房间设备管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BRoomEquipment bRoomEquipment)
    {
        return toAjax(bRoomEquipmentService.updateBRoomEquipment(bRoomEquipment));
    }

    /**
     * 删除房间设备管理
     */
    @PreAuthorize("@ss.hasPermi('shop:equipment:remove')")
    @Log(title = "房间设备管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bRoomEquipmentService.deleteBRoomEquipmentByIds(ids));
    }
}
