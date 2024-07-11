package com.hewei.marketingmanage.service;

import java.util.List;
import com.hewei.marketingmanage.domain.DpSysSmsTemplate;

/**
 * 消息模版（短信|微信|邮件）Service接口
 * 
 * @author hewei
 * @date 2024-07-03
 */
public interface IDpSysSmsTemplateService 
{
    /**
     * 查询消息模版（短信|微信|邮件）
     * 
     * @param id 消息模版（短信|微信|邮件）主键
     * @return 消息模版（短信|微信|邮件）
     */
    public DpSysSmsTemplate selectDpSysSmsTemplateById(Long id);

    /**
     * 查询消息模版（短信|微信|邮件）列表
     * 
     * @param dpSysSmsTemplate 消息模版（短信|微信|邮件）
     * @return 消息模版（短信|微信|邮件）集合
     */
    public List<DpSysSmsTemplate> selectDpSysSmsTemplateList(DpSysSmsTemplate dpSysSmsTemplate);
    List<DpSysSmsTemplate> getDictList(String templateType);
    /**
     * 新增消息模版（短信|微信|邮件）
     * 
     * @param dpSysSmsTemplate 消息模版（短信|微信|邮件）
     * @return 结果
     */
    public int insertDpSysSmsTemplate(DpSysSmsTemplate dpSysSmsTemplate);

    /**
     * 修改消息模版（短信|微信|邮件）
     * 
     * @param dpSysSmsTemplate 消息模版（短信|微信|邮件）
     * @return 结果
     */
    public int updateDpSysSmsTemplate(DpSysSmsTemplate dpSysSmsTemplate);

    /**
     * 批量删除消息模版（短信|微信|邮件）
     * 
     * @param ids 需要删除的消息模版（短信|微信|邮件）主键集合
     * @return 结果
     */
    public int deleteDpSysSmsTemplateByIds(Long[] ids);

    /**
     * 删除消息模版（短信|微信|邮件）信息
     * 
     * @param id 消息模版（短信|微信|邮件）主键
     * @return 结果
     */
    public int deleteDpSysSmsTemplateById(Long id);


}
