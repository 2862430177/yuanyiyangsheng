package com.hewei.marketingmanage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hewei.marketingmanage.mapper.DpSysSmsTemplateMapper;
import com.hewei.marketingmanage.domain.DpSysSmsTemplate;
import com.hewei.marketingmanage.service.IDpSysSmsTemplateService;

/**
 * 消息模版（短信|微信|邮件）Service业务层处理
 * 
 * @author hewei
 * @date 2024-07-03
 */
@Service
public class DpSysSmsTemplateServiceImpl implements IDpSysSmsTemplateService 
{
    @Autowired
    private DpSysSmsTemplateMapper dpSysSmsTemplateMapper;

    /**
     * 查询消息模版（短信|微信|邮件）
     * 
     * @param id 消息模版（短信|微信|邮件）主键
     * @return 消息模版（短信|微信|邮件）
     */
    @Override
    public DpSysSmsTemplate selectDpSysSmsTemplateById(Long id)
    {
        return dpSysSmsTemplateMapper.selectDpSysSmsTemplateById(id);
    }

    /**
     * 查询消息模版（短信|微信|邮件）列表
     * 
     * @param dpSysSmsTemplate 消息模版（短信|微信|邮件）
     * @return 消息模版（短信|微信|邮件）
     */
    @Override
    public List<DpSysSmsTemplate> selectDpSysSmsTemplateList(DpSysSmsTemplate dpSysSmsTemplate)
    {
        return dpSysSmsTemplateMapper.selectDpSysSmsTemplateList(dpSysSmsTemplate);
    }

    @Override
    public List<DpSysSmsTemplate> getDictList(String templateType) {
        DpSysSmsTemplate queryCondition = new DpSysSmsTemplate();
        queryCondition.setTemplateType(templateType);
        return this.selectDpSysSmsTemplateList(queryCondition);
    }

    /**
     * 新增消息模版（短信|微信|邮件）
     * 
     * @param dpSysSmsTemplate 消息模版（短信|微信|邮件）
     * @return 结果
     */
    @Override
    public int insertDpSysSmsTemplate(DpSysSmsTemplate dpSysSmsTemplate)
    {
        dpSysSmsTemplate.setCreateTime(DateUtils.getNowDate());
        return dpSysSmsTemplateMapper.insertDpSysSmsTemplate(dpSysSmsTemplate);
    }

    /**
     * 修改消息模版（短信|微信|邮件）
     * 
     * @param dpSysSmsTemplate 消息模版（短信|微信|邮件）
     * @return 结果
     */
    @Override
    public int updateDpSysSmsTemplate(DpSysSmsTemplate dpSysSmsTemplate)
    {
        dpSysSmsTemplate.setUpdateTime(DateUtils.getNowDate());
        return dpSysSmsTemplateMapper.updateDpSysSmsTemplate(dpSysSmsTemplate);
    }

    /**
     * 批量删除消息模版（短信|微信|邮件）
     * 
     * @param ids 需要删除的消息模版（短信|微信|邮件）主键
     * @return 结果
     */
    @Override
    public int deleteDpSysSmsTemplateByIds(Long[] ids)
    {
        return dpSysSmsTemplateMapper.deleteDpSysSmsTemplateByIds(ids);
    }

    /**
     * 删除消息模版（短信|微信|邮件）信息
     * 
     * @param id 消息模版（短信|微信|邮件）主键
     * @return 结果
     */
    @Override
    public int deleteDpSysSmsTemplateById(Long id)
    {
        return dpSysSmsTemplateMapper.deleteDpSysSmsTemplateById(id);
    }
}
