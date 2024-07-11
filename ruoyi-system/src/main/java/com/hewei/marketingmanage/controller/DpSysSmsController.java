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
import com.hewei.marketingmanage.domain.DpSysSms;
import com.hewei.marketingmanage.service.IDpSysSmsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 消息记录（短信|微信|邮件）Controller
 * 
 * @author hewei
 * @date 2024-07-03
 */
@RestController
@RequestMapping("/marketingmanage/sms")
public class DpSysSmsController extends BaseController
{
    @Autowired
    private IDpSysSmsService dpSysSmsService;

    /**
     * 查询消息记录（短信|微信|邮件）列表
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:sms:list')")
    @GetMapping("/list")
    public TableDataInfo list(DpSysSms dpSysSms)
    {
        startPage();
        List<DpSysSms> list = dpSysSmsService.selectDpSysSmsList(dpSysSms);
        return getDataTable(list);
    }

    /**
     * 导出消息记录（短信|微信|邮件）列表
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:sms:export')")
    @Log(title = "消息记录（短信|微信|邮件）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DpSysSms dpSysSms)
    {
        List<DpSysSms> list = dpSysSmsService.selectDpSysSmsList(dpSysSms);
        ExcelUtil<DpSysSms> util = new ExcelUtil<DpSysSms>(DpSysSms.class);
        util.exportExcel(response, list, "消息记录（短信|微信|邮件）数据");
    }

    /**
     * 获取消息记录（短信|微信|邮件）详细信息
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:sms:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(dpSysSmsService.selectDpSysSmsById(id));
    }

    /**
     * 新增消息记录（短信|微信|邮件）
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:sms:add')")
    @Log(title = "消息记录（短信|微信|邮件）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DpSysSms dpSysSms)
    {
        return toAjax(dpSysSmsService.insertDpSysSms(dpSysSms));
    }

    /**
     * 修改消息记录（短信|微信|邮件）
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:sms:edit')")
    @Log(title = "消息记录（短信|微信|邮件）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DpSysSms dpSysSms)
    {
        return toAjax(dpSysSmsService.updateDpSysSms(dpSysSms));
    }

    /**
     * 删除消息记录（短信|微信|邮件）
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:sms:remove')")
    @Log(title = "消息记录（短信|微信|邮件）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dpSysSmsService.deleteDpSysSmsByIds(ids));
    }
}
