package com.hewei.marketingmanage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.hewei.marketingmanage.domain.DpSysSmsTemplate;
import com.hewei.marketingmanage.service.IDpSysSmsTemplateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 消息模版（短信|微信|邮件）Controller
 * 
 * @author hewei
 * @date 2024-07-03
 */
@RestController
@RequestMapping("/marketingmanage/template")
public class DpSysSmsTemplateController extends BaseController
{
    @Autowired
    private IDpSysSmsTemplateService dpSysSmsTemplateService;

    /**
     * 获取消息模版数据字典list
     */
    @GetMapping("/getDictList")
    public AjaxResult getDictList(@RequestParam(value = "templateType",required = false) String templateType){
        List<DpSysSmsTemplate> list = dpSysSmsTemplateService.getDictList(templateType);
        return success(list);
    }

    /**
     * 查询消息模版（短信|微信|邮件）列表
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(DpSysSmsTemplate dpSysSmsTemplate)
    {
        startPage();
        List<DpSysSmsTemplate> list = dpSysSmsTemplateService.selectDpSysSmsTemplateList(dpSysSmsTemplate);
        return getDataTable(list);
    }

    /**
     * 导出消息模版（短信|微信|邮件）列表
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:template:export')")
    @Log(title = "消息模版（短信|微信|邮件）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DpSysSmsTemplate dpSysSmsTemplate)
    {
        List<DpSysSmsTemplate> list = dpSysSmsTemplateService.selectDpSysSmsTemplateList(dpSysSmsTemplate);
        ExcelUtil<DpSysSmsTemplate> util = new ExcelUtil<DpSysSmsTemplate>(DpSysSmsTemplate.class);
        util.exportExcel(response, list, "消息模版（短信|微信|邮件）数据");
    }

    /**
     * 获取消息模版（短信|微信|邮件）详细信息
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:template:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(dpSysSmsTemplateService.selectDpSysSmsTemplateById(id));
    }

    /**
     * 新增消息模版（短信|微信|邮件）
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:template:add')")
    @Log(title = "消息模版（短信|微信|邮件）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DpSysSmsTemplate dpSysSmsTemplate)
    {
        return toAjax(dpSysSmsTemplateService.insertDpSysSmsTemplate(dpSysSmsTemplate));
    }

    /**
     * 修改消息模版（短信|微信|邮件）
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:template:edit')")
    @Log(title = "消息模版（短信|微信|邮件）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DpSysSmsTemplate dpSysSmsTemplate)
    {
        return toAjax(dpSysSmsTemplateService.updateDpSysSmsTemplate(dpSysSmsTemplate));
    }

    /**
     * 删除消息模版（短信|微信|邮件）
     */
    @PreAuthorize("@ss.hasPermi('marketingmanage:template:remove')")
    @Log(title = "消息模版（短信|微信|邮件）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dpSysSmsTemplateService.deleteDpSysSmsTemplateByIds(ids));
    }
}
