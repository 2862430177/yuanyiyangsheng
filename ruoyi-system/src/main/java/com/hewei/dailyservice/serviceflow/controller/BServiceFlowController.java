package com.hewei.dailyservice.serviceflow.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.hewei.dailyservice.serviceflow.vo.BServiceFlowQueryVo;
import com.hewei.dailyservice.serviceflow.vo.BServiceFlowResultVo;
import com.hewei.dailyservice.serviceflow.vo.BServiceFlowSettleVo;
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
import com.hewei.dailyservice.serviceflow.domain.BServiceFlow;
import com.hewei.dailyservice.serviceflow.service.IBServiceFlowService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 服务流水,一次服务一条流水记录Controller
 * 
 * @author hewei
 * @date 2024-06-05
 */
@RestController
@RequestMapping("/dailyservice/flow")
public class BServiceFlowController extends BaseController
{
    @Autowired
    private IBServiceFlowService bServiceFlowService;

    /**
     * 查询服务流水,一次服务一条流水记录列表
     */
    @PreAuthorize("@ss.hasPermi('dailyservice:flow:list')")
    @GetMapping("/list")
    public TableDataInfo list(BServiceFlowQueryVo serviceFlowQueryVo)
    {
        startPage();
        List<BServiceFlowResultVo> list = bServiceFlowService.selectBServiceFlowList(serviceFlowQueryVo);
        return getDataTable(list);
    }

    /**
     * 导出服务流水,一次服务一条流水记录列表
     */
    @PreAuthorize("@ss.hasPermi('dailyservice:flow:export')")
    @Log(title = "服务流水,一次服务一条流水记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BServiceFlow bServiceFlow)
    {
        List<BServiceFlow> list = bServiceFlowService.selectBServiceFlowList(bServiceFlow);
        ExcelUtil<BServiceFlow> util = new ExcelUtil<BServiceFlow>(BServiceFlow.class);
        util.exportExcel(response, list, "服务流水,一次服务一条流水记录数据");
    }

    /**
     * 获取服务流水,一次服务一条流水记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('dailyservice:flow:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bServiceFlowService.selectBServiceFlowById(id));
    }

    /**
     * 新增服务流水,一次服务一条流水记录
     */
    @PreAuthorize("@ss.hasPermi('dailyservice:flow:add')")
    @Log(title = "服务流水,一次服务一条流水记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BServiceFlow bServiceFlow)
    {
        return toAjax(bServiceFlowService.insertBServiceFlow(bServiceFlow));
    }

    /**
     * 确定结算
     * @param serviceFlowSettleVo
     * @return
     */
    @PostMapping(value = "confirmSettle")
    public AjaxResult confirmSettle(@RequestBody BServiceFlowSettleVo serviceFlowSettleVo)
    {
        return bServiceFlowService.settle(serviceFlowSettleVo);
    }

    /**
     * 确定重新结算
     * @param serviceFlowSettleVo
     * @return
     */
    @PostMapping(value = "confirmReSettle")
    public AjaxResult confirmReSettle(@RequestBody BServiceFlowSettleVo serviceFlowSettleVo)
    {
        return bServiceFlowService.reSettle(serviceFlowSettleVo);
    }



    /**
     * 修改服务流水,一次服务一条流水记录
     */
    @PreAuthorize("@ss.hasPermi('dailyservice:flow:edit')")
    @Log(title = "服务流水,一次服务一条流水记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BServiceFlow bServiceFlow)
    {
        return toAjax(bServiceFlowService.updateBServiceFlow(bServiceFlow));
    }

    /**
     * 删除服务流水,一次服务一条流水记录
     */
    @PreAuthorize("@ss.hasPermi('dailyservice:flow:remove')")
    @Log(title = "服务流水,一次服务一条流水记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bServiceFlowService.deleteBServiceFlowByIds(ids));
    }
}
