package com.hewei.employee.controller;

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
import com.hewei.employee.domain.BEmployee;
import com.hewei.employee.service.IBEmployeeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 员工信息管理Controller
 * 
 * @author hewei
 * @date 2024-06-04
 */
@RestController
@RequestMapping("/employee/employee")
public class BEmployeeController extends BaseController
{
    @Autowired
    private IBEmployeeService bEmployeeService;

    /**
     * 查询员工信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('employee:employee:list')")
    @GetMapping("/list")
    public TableDataInfo list(BEmployee bEmployee)
    {
        startPage();
        List<BEmployee> list = bEmployeeService.selectBEmployeeList(bEmployee);
        return getDataTable(list);
    }

    /**
     * 导出员工信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('employee:employee:export')")
    @Log(title = "员工信息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BEmployee bEmployee)
    {
        List<BEmployee> list = bEmployeeService.selectBEmployeeList(bEmployee);
        ExcelUtil<BEmployee> util = new ExcelUtil<BEmployee>(BEmployee.class);
        util.exportExcel(response, list, "员工信息管理数据");
    }

    /**
     * 获取员工信息管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('employee:employee:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bEmployeeService.selectBEmployeeById(id));
    }

    /**
     * 新增员工信息管理
     */
    @PreAuthorize("@ss.hasPermi('employee:employee:add')")
    @Log(title = "员工信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BEmployee bEmployee)
    {
        return toAjax(bEmployeeService.insertBEmployee(bEmployee));
    }

    /**
     * 修改员工信息管理
     */
    @PreAuthorize("@ss.hasPermi('employee:employee:edit')")
    @Log(title = "员工信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BEmployee bEmployee)
    {
        return toAjax(bEmployeeService.updateBEmployee(bEmployee));
    }

    /**
     * 删除员工信息管理
     */
    @PreAuthorize("@ss.hasPermi('employee:employee:remove')")
    @Log(title = "员工信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bEmployeeService.deleteBEmployeeByIds(ids));
    }
}
