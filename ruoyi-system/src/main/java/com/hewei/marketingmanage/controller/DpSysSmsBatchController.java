package com.hewei.marketingmanage.controller;

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
import com.hewei.marketingmanage.domain.DpSysSmsBatch;
import com.hewei.marketingmanage.service.IDpSysSmsBatchService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 短信消息批量发送定时执行状态Controller
 * 
 * @author hewei
 * @date 2024-07-08
 */
@RestController
@RequestMapping("/marketingmanage/batch")
public class DpSysSmsBatchController extends BaseController
{
    @Autowired
    private IDpSysSmsBatchService dpSysSmsBatchService;

    /**
     * 查询短信消息批量发送定时执行状态列表
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:batch:list')")
    @GetMapping("/list")
    public TableDataInfo list(DpSysSmsBatch dpSysSmsBatch)
    {
        startPage();
        List<DpSysSmsBatch> list = dpSysSmsBatchService.selectDpSysSmsBatchList(dpSysSmsBatch);
        return getDataTable(list);
    }

    /**
     * 导出短信消息批量发送定时执行状态列表
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:batch:export')")
    @Log(title = "短信消息批量发送定时执行状态", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DpSysSmsBatch dpSysSmsBatch)
    {
        List<DpSysSmsBatch> list = dpSysSmsBatchService.selectDpSysSmsBatchList(dpSysSmsBatch);
        ExcelUtil<DpSysSmsBatch> util = new ExcelUtil<DpSysSmsBatch>(DpSysSmsBatch.class);
        util.exportExcel(response, list, "短信消息批量发送定时执行状态数据");
    }

    /**
     * 获取短信消息批量发送定时执行状态详细信息
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:batch:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(dpSysSmsBatchService.selectDpSysSmsBatchById(id));
    }

    /**
     * 新增短信消息批量发送定时执行状态
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:batch:add')")
    @Log(title = "短信消息批量发送定时执行状态", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DpSysSmsBatch dpSysSmsBatch)
    {
        return toAjax(dpSysSmsBatchService.insertDpSysSmsBatch(dpSysSmsBatch));
    }

    /**
     * 修改短信消息批量发送定时执行状态
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:batch:edit')")
    @Log(title = "短信消息批量发送定时执行状态", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DpSysSmsBatch dpSysSmsBatch)
    {
        return toAjax(dpSysSmsBatchService.updateDpSysSmsBatch(dpSysSmsBatch));
    }

    /**
     * 删除短信消息批量发送定时执行状态
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:batch:remove')")
    @Log(title = "短信消息批量发送定时执行状态", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dpSysSmsBatchService.deleteDpSysSmsBatchByIds(ids));
    }
}
