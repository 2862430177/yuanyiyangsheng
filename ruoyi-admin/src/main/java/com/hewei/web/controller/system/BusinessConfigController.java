package com.hewei.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 参数配置 信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/config")
public class BusinessConfigController extends BaseController
{
    @Autowired
    private ISysConfigService configService;
    /**
     * 修改参数配置
     */
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    @PostMapping(value = "updateBykey")
    public AjaxResult updateBykey(@RequestBody SysConfig config)
    {
        config.setUpdateBy(getUsername());
        return toAjax(configService.updateConfigByKey(config));
    }
}
