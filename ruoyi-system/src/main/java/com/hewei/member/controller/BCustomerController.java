package com.hewei.member.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.hewei.member.vo.BCustomerQueryVo;
import com.hewei.member.vo.BCustomerResultVo;
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
import com.hewei.member.domain.BCustomer;
import com.hewei.member.service.IBCustomerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 会员信息管理Controller
 * 
 * @author hewei
 * @date 2024-06-04
 */
@RestController
@RequestMapping("/member/customer")
public class BCustomerController extends BaseController
{
    @Autowired
    private IBCustomerService bCustomerService;

    /**
     * 查询会员信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('member:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(BCustomer bCustomer)
    {
        startPage();
        List<BCustomer> list = bCustomerService.selectBCustomerList(bCustomer);
        return getDataTable(list);
    }

    /**
     * 导出会员信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('member:customer:export')")
    @Log(title = "会员信息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BCustomer bCustomer)
    {
        List<BCustomer> list = bCustomerService.selectBCustomerList(bCustomer);
        ExcelUtil<BCustomer> util = new ExcelUtil<BCustomer>(BCustomer.class);
        util.exportExcel(response, list, "会员信息管理数据");
    }

    /**
     * 获取会员信息管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('member:customer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bCustomerService.selectBCustomerById(id));
    }

    /**
     * 获取会员信息,附带会员卡信息
     */
    @GetMapping(value = "/getCustomerInfoWithMemberCard")
    public TableDataInfo getCustomerInfoWithMemberCard(BCustomerQueryVo queryVo)
    {
        startPage();
        List<BCustomerResultVo> list = bCustomerService.selectBCustomerWithMemberCard(queryVo);
        return getDataTable(list);
    }

    /**
     * 新增会员信息管理
     */
    @PreAuthorize("@ss.hasPermi('member:customer:add')")
    @Log(title = "会员信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BCustomer bCustomer)
    {
        return toAjax(bCustomerService.insertBCustomer(bCustomer));
    }

    /**
     * 修改会员信息管理
     */
    @PreAuthorize("@ss.hasPermi('member:customer:edit')")
    @Log(title = "会员信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BCustomer bCustomer)
    {
        return toAjax(bCustomerService.updateBCustomer(bCustomer));
    }

    /**
     * 删除会员信息管理
     */
    @PreAuthorize("@ss.hasPermi('member:customer:remove')")
    @Log(title = "会员信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bCustomerService.deleteBCustomerByIds(ids));
    }
}
