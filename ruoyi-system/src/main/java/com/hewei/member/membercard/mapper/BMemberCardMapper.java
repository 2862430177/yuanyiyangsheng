package com.hewei.member.membercard.mapper;

import java.util.List;
import com.hewei.member.membercard.domain.BMemberCard;

/**
 * 会员卡信息管理Mapper接口
 * 
 * @author hewei
 * @date 2024-06-04
 */
public interface BMemberCardMapper 
{
    /**
     * 查询会员卡信息管理
     * 
     * @param id 会员卡信息管理主键
     * @return 会员卡信息管理
     */
    public BMemberCard selectBMemberCardById(Long id);

    /**
     * 查询会员卡信息管理列表
     * 
     * @param bMemberCard 会员卡信息管理
     * @return 会员卡信息管理集合
     */
    public List<BMemberCard> selectBMemberCardList(BMemberCard bMemberCard);

    /**
     * 新增会员卡信息管理
     * 
     * @param bMemberCard 会员卡信息管理
     * @return 结果
     */
    public int insertBMemberCard(BMemberCard bMemberCard);

    /**
     * 修改会员卡信息管理
     * 
     * @param bMemberCard 会员卡信息管理
     * @return 结果
     */
    public int updateBMemberCard(BMemberCard bMemberCard);

    /**
     * 删除会员卡信息管理
     * 
     * @param id 会员卡信息管理主键
     * @return 结果
     */
    public int deleteBMemberCardById(Long id);

    /**
     * 批量删除会员卡信息管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBMemberCardByIds(Long[] ids);
}
