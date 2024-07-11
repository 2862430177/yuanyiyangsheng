package com.hewei.shop.serviceitems.controller;

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
import com.hewei.shop.serviceitems.domain.BServiceItems;
import com.hewei.shop.serviceitems.service.IBServiceItemsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 服务项目管理Controller
 * 
 * @author hewei
 * @date 2024-06-04
 */
@RestController
@RequestMapping("/serviceitems/serviceitems")
public class BServiceItemsController extends BaseController
{
    @Autowired
    private IBServiceItemsService bServiceItemsService;

    /**
     * 查询服务项目管理列表
     */
    @PreAuthorize("@ss.hasPermi('serviceitems:serviceitems:list')")
    @GetMapping("/list")
    public TableDataInfo list(BServiceItems bServiceItems)
    {
        startPage();
        List<BServiceItems> list = bServiceItemsService.selectBServiceItemsList(bServiceItems);
        return getDataTable(list);
    }

    /**
     * 导出服务项目管理列表
     */
    @PreAuthorize("@ss.hasPermi('serviceitems:serviceitems:export')")
    @Log(title = "服务项目管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BServiceItems bServiceItems)
    {
        List<BServiceItems> list = bServiceItemsService.selectBServiceItemsList(bServiceItems);
        ExcelUtil<BServiceItems> util = new ExcelUtil<BServiceItems>(BServiceItems.class);
        util.exportExcel(response, list, "服务项目管理数据");
    }

    /**
     * 获取服务项目管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('serviceitems:serviceitems:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bServiceItemsService.selectBServiceItemsById(id));
    }

    /**
     * 新增服务项目管理
     */
    @PreAuthorize("@ss.hasPermi('serviceitems:serviceitems:add')")
    @Log(title = "服务项目管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BServiceItems bServiceItems)
    {
        return toAjax(bServiceItemsService.insertBServiceItems(bServiceItems));
    }

    /**
     * 修改服务项目管理
     */
    @PreAuthorize("@ss.hasPermi('serviceitems:serviceitems:edit')")
    @Log(title = "服务项目管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BServiceItems bServiceItems)
    {
        return toAjax(bServiceItemsService.updateBServiceItems(bServiceItems));
    }

    /**
     * 删除服务项目管理
     */
    @PreAuthorize("@ss.hasPermi('serviceitems:serviceitems:remove')")
    @Log(title = "服务项目管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bServiceItemsService.deleteBServiceItemsByIds(ids));
    }
}
