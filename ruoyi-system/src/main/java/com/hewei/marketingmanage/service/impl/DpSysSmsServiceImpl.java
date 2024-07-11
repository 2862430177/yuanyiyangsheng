package com.hewei.marketingmanage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hewei.marketingmanage.mapper.DpSysSmsMapper;
import com.hewei.marketingmanage.domain.DpSysSms;
import com.hewei.marketingmanage.service.IDpSysSmsService;

/**
 * 消息记录（短信|微信|邮件）Service业务层处理
 * 
 * @author hewei
 * @date 2024-07-03
 */
@Service
public class DpSysSmsServiceImpl implements IDpSysSmsService 
{
    @Autowired
    private DpSysSmsMapper dpSysSmsMapper;

    /**
     * 查询消息记录（短信|微信|邮件）
     * 
     * @param id 消息记录（短信|微信|邮件）主键
     * @return 消息记录（短信|微信|邮件）
     */
    @Override
    public DpSysSms selectDpSysSmsById(Long id)
    {
        return dpSysSmsMapper.selectDpSysSmsById(id);
    }

    /**
     * 查询消息记录（短信|微信|邮件）列表
     * 
     * @param dpSysSms 消息记录（短信|微信|邮件）
     * @return 消息记录（短信|微信|邮件）
     */
    @Override
    public List<DpSysSms> selectDpSysSmsList(DpSysSms dpSysSms)
    {
        return dpSysSmsMapper.selectDpSysSmsList(dpSysSms);
    }

    /**
     * 新增消息记录（短信|微信|邮件）
     * 
     * @param dpSysSms 消息记录（短信|微信|邮件）
     * @return 结果
     */
    @Override
    public int insertDpSysSms(DpSysSms dpSysSms)
    {
        dpSysSms.setCreateTime(DateUtils.getNowDate());
        return dpSysSmsMapper.insertDpSysSms(dpSysSms);
    }

    /**
     * 修改消息记录（短信|微信|邮件）
     * 
     * @param dpSysSms 消息记录（短信|微信|邮件）
     * @return 结果
     */
    @Override
    public int updateDpSysSms(DpSysSms dpSysSms)
    {
        dpSysSms.setUpdateTime(DateUtils.getNowDate());
        return dpSysSmsMapper.updateDpSysSms(dpSysSms);
    }

    /**
     * 批量删除消息记录（短信|微信|邮件）
     * 
     * @param ids 需要删除的消息记录（短信|微信|邮件）主键
     * @return 结果
     */
    @Override
    public int deleteDpSysSmsByIds(Long[] ids)
    {
        return dpSysSmsMapper.deleteDpSysSmsByIds(ids);
    }

    /**
     * 删除消息记录（短信|微信|邮件）信息
     * 
     * @param id 消息记录（短信|微信|邮件）主键
     * @return 结果
     */
    @Override
    public int deleteDpSysSmsById(Long id)
    {
        return dpSysSmsMapper.deleteDpSysSmsById(id);
    }
}
