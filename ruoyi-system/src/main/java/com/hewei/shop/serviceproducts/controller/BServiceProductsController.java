package com.hewei.shop.serviceproducts.controller;

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
import com.hewei.shop.serviceproducts.domain.BServiceProducts;
import com.hewei.shop.serviceproducts.service.IBServiceProductsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 服务产品管理Controller
 * 
 * @author hewei
 * @date 2024-06-04
 */
@RestController
@RequestMapping("/serviceproducts/serviceproducts")
public class BServiceProductsController extends BaseController
{
    @Autowired
    private IBServiceProductsService bServiceProductsService;

    /**
     * 查询服务产品管理列表
     */
    @PreAuthorize("@ss.hasPermi('serviceproducts:serviceproducts:list')")
    @GetMapping("/list")
    public TableDataInfo list(BServiceProducts bServiceProducts)
    {
        startPage();
        List<BServiceProducts> list = bServiceProductsService.selectBServiceProductsList(bServiceProducts);
        return getDataTable(list);
    }

    /**
     * 导出服务产品管理列表
     */
    @PreAuthorize("@ss.hasPermi('serviceproducts:serviceproducts:export')")
    @Log(title = "服务产品管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BServiceProducts bServiceProducts)
    {
        List<BServiceProducts> list = bServiceProductsService.selectBServiceProductsList(bServiceProducts);
        ExcelUtil<BServiceProducts> util = new ExcelUtil<BServiceProducts>(BServiceProducts.class);
        util.exportExcel(response, list, "服务产品管理数据");
    }

    /**
     * 获取服务产品管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('serviceproducts:serviceproducts:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bServiceProductsService.selectBServiceProductsById(id));
    }

    /**
     * 新增服务产品管理
     */
    @PreAuthorize("@ss.hasPermi('serviceproducts:serviceproducts:add')")
    @Log(title = "服务产品管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BServiceProducts bServiceProducts)
    {
        return toAjax(bServiceProductsService.insertBServiceProducts(bServiceProducts));
    }

    /**
     * 修改服务产品管理
     */
    @PreAuthorize("@ss.hasPermi('serviceproducts:serviceproducts:edit')")
    @Log(title = "服务产品管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BServiceProducts bServiceProducts)
    {
        return toAjax(bServiceProductsService.updateBServiceProducts(bServiceProducts));
    }

    /**
     * 删除服务产品管理
     */
    @PreAuthorize("@ss.hasPermi('serviceproducts:serviceproducts:remove')")
    @Log(title = "服务产品管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bServiceProductsService.deleteBServiceProductsByIds(ids));
    }
}
