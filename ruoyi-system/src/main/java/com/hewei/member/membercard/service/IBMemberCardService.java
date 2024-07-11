package com.hewei.member.membercard.service;

import java.util.List;
import com.hewei.member.membercard.domain.BMemberCard;
import com.hewei.member.membercard.vo.BMemberCardQueryVo;
import com.hewei.member.membercard.vo.BMemberCardRechargeVo;
import com.hewei.member.membercard.vo.BMemberCardResultVo;

/**
 * 会员卡信息管理Service接口
 * 
 * @author hewei
 * @date 2024-06-04
 */
public interface IBMemberCardService 
{
    /**
     * 查询会员卡信息管理
     * 
     * @param id 会员卡信息管理主键
     * @return 会员卡信息管理
     */
    BMemberCard selectBMemberCardById(Long id);

    BMemberCardResultVo selectMembercardInfoById(Long id);
    BMemberCardResultVo selectMembercardInfoByMemberInfo(BMemberCardQueryVo memberCardQueryVo);
    /**
     * 查询会员卡信息管理列表
     * 
     * @param bMemberCard 会员卡信息管理
     * @return 会员卡信息管理集合
     */
    List<BMemberCard> selectBMemberCardList(BMemberCard bMemberCard);
    List<BMemberCardResultVo> selectBMemberCardList(BMemberCardQueryVo bMemberCardQueryVo);
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
     * 会员卡充值
     * @param memberCardRechargeVo
     * @return
     */
    int recharge(BMemberCardRechargeVo memberCardRechargeVo);
    /**
     * 批量删除会员卡信息管理
     * 
     * @param ids 需要删除的会员卡信息管理主键集合
     * @return 结果
     */
    public int deleteBMemberCardByIds(Long[] ids);

    /**
     * 删除会员卡信息管理信息
     * 
     * @param id 会员卡信息管理主键
     * @return 结果
     */
    public int deleteBMemberCardById(Long id);
}
