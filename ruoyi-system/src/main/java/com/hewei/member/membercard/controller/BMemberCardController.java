package com.hewei.member.membercard.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.hewei.member.membercard.vo.BMemberCardQueryVo;
import com.hewei.member.membercard.vo.BMemberCardRechargeVo;
import com.hewei.member.membercard.vo.BMemberCardResultVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.hewei.member.membercard.domain.BMemberCard;
import com.hewei.member.membercard.service.IBMemberCardService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 会员卡信息管理Controller
 * 
 * @author hewei
 * @date 2024-06-04
 */
@RestController
@RequestMapping("/member/membercard")
public class BMemberCardController extends BaseController
{
    @Autowired
    private IBMemberCardService bMemberCardService;

    /**
     * 查询会员卡信息管理列表
     */
//    @PreAuthorize("@ss.hasPermi('member:membercard:list')")
//    @GetMapping("/list")
//    public TableDataInfo list(BMemberCard bMemberCard)
//    {
//        startPage();
//        List<BMemberCard> list = bMemberCardService.selectBMemberCardList(bMemberCard);
//        return getDataTable(list);
//    }

    /**
     * 查询会员卡信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('member:membercard:list')")
    @GetMapping("/list")
    public TableDataInfo list(BMemberCardQueryVo bMemberCardQueryVo)
    {
        startPage();
        List<BMemberCardResultVo> list = bMemberCardService.selectBMemberCardList(bMemberCardQueryVo);
        return getDataTable(list);
    }


    /**
     * 导出会员卡信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('member:membercard:export')")
    @Log(title = "会员卡信息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BMemberCard bMemberCard)
    {
        List<BMemberCard> list = bMemberCardService.selectBMemberCardList(bMemberCard);
        ExcelUtil<BMemberCard> util = new ExcelUtil<BMemberCard>(BMemberCard.class);
        util.exportExcel(response, list, "会员卡信息管理数据");
    }

    /**
     * 获取会员卡信息管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('member:membercard:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bMemberCardService.selectBMemberCardById(id));
    }
    /**
     * 获取会员卡信息管理详细信息
     * @Return BMemberCardResultVo
     */
    @GetMapping(value = "/getMembercardInfo/{id}")
    public AjaxResult getMembercardInfo(@PathVariable("id") Long id)
    {
        return success(bMemberCardService.selectMembercardInfoById(id));
    }

    /**
     * 根据会员信息查询会员卡信息
     * @param memberCardQueryVo 会员id或手机号
     * @return
     */
    @GetMapping(value = "/getMembercardInfoByMemberinfo")
    public AjaxResult getMembercardInfoByMemberinfo(BMemberCardQueryVo memberCardQueryVo)
    {
        return success(bMemberCardService.selectMembercardInfoByMemberInfo(memberCardQueryVo));
    }


    /**
     * 会员卡充值
     */
    @Log(title = "会员卡信息管理", businessType = BusinessType.UPDATE)
    @PostMapping("recharge")
    public AjaxResult recharge(@RequestBody BMemberCardRechargeVo memberCardRechargeVo)
    {
        return toAjax(bMemberCardService.recharge(memberCardRechargeVo));
    }

    /**
     * 新增会员卡信息管理
     */
    @PreAuthorize("@ss.hasPermi('member:membercard:add')")
    @Log(title = "会员卡信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BMemberCard bMemberCard)
    {
        return toAjax(bMemberCardService.insertBMemberCard(bMemberCard));
    }

    /**
     * 修改会员卡信息管理
     */
    @PreAuthorize("@ss.hasPermi('member:membercard:edit')")
    @Log(title = "会员卡信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BMemberCard bMemberCard)
    {
        return toAjax(bMemberCardService.updateBMemberCard(bMemberCard));
    }

    /**
     * 删除会员卡信息管理
     */
    @PreAuthorize("@ss.hasPermi('member:membercard:remove')")
    @Log(title = "会员卡信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bMemberCardService.deleteBMemberCardByIds(ids));
    }
}
