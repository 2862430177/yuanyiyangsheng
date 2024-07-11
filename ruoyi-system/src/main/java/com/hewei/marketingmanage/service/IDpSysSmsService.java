package com.hewei.marketingmanage.service;

import java.util.List;
import com.hewei.marketingmanage.domain.DpSysSms;

/**
 * 消息记录（短信|微信|邮件）Service接口
 * 
 * @author hewei
 * @date 2024-07-03
 */
public interface IDpSysSmsService 
{
    /**
     * 查询消息记录（短信|微信|邮件）
     * 
     * @param id 消息记录（短信|微信|邮件）主键
     * @return 消息记录（短信|微信|邮件）
     */
    public DpSysSms selectDpSysSmsById(Long id);

    /**
     * 查询消息记录（短信|微信|邮件）列表
     * 
     * @param dpSysSms 消息记录（短信|微信|邮件）
     * @return 消息记录（短信|微信|邮件）集合
     */
    public List<DpSysSms> selectDpSysSmsList(DpSysSms dpSysSms);

    /**
     * 新增消息记录（短信|微信|邮件）
     * 
     * @param dpSysSms 消息记录（短信|微信|邮件）
     * @return 结果
     */
    public int insertDpSysSms(DpSysSms dpSysSms);

    /**
     * 修改消息记录（短信|微信|邮件）
     * 
     * @param dpSysSms 消息记录（短信|微信|邮件）
     * @return 结果
     */
    public int updateDpSysSms(DpSysSms dpSysSms);

    /**
     * 批量删除消息记录（短信|微信|邮件）
     * 
     * @param ids 需要删除的消息记录（短信|微信|邮件）主键集合
     * @return 结果
     */
    public int deleteDpSysSmsByIds(Long[] ids);

    /**
     * 删除消息记录（短信|微信|邮件）信息
     * 
     * @param id 消息记录（短信|微信|邮件）主键
     * @return 结果
     */
    public int deleteDpSysSmsById(Long id);
}
