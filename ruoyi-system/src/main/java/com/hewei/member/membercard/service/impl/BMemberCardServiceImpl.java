package com.hewei.member.membercard.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.hewei.employee.domain.BEmployee;
import com.hewei.employee.service.IBEmployeeService;
import com.hewei.member.domain.BCustomer;
import com.hewei.member.membercard.vo.BMemberCardQueryVo;
import com.hewei.member.membercard.vo.BMemberCardRechargeVo;
import com.hewei.member.membercard.vo.BMemberCardResultVo;
import com.hewei.member.service.IBCustomerService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hewei.member.membercard.mapper.BMemberCardMapper;
import com.hewei.member.membercard.domain.BMemberCard;
import com.hewei.member.membercard.service.IBMemberCardService;

/**
 * 会员卡信息管理Service业务层处理
 * 
 * @author hewei
 * @date 2024-06-04
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BMemberCardServiceImpl implements IBMemberCardService 
{
    private final BMemberCardMapper bMemberCardMapper;
    private final IBCustomerService customerService;
    private final IBEmployeeService employeeService;
    /**
     * 查询会员卡信息管理
     * 
     * @param id 会员卡信息管理主键
     * @return 会员卡信息管理
     */
    @Override
    public BMemberCard selectBMemberCardById(Long id)
    {
        return bMemberCardMapper.selectBMemberCardById(id);
    }

    @Override
    public BMemberCardResultVo selectMembercardInfoById(Long id) {

        BMemberCardResultVo memberCardResultVo = new BMemberCardResultVo();

        BMemberCard memberCard = this.selectBMemberCardById(id);
        if(memberCard != null){
            BeanUtils.copyBeanProp(memberCardResultVo,memberCard);
            Long customerId = memberCard.getCustomerId();
            if(customerId != null){
                BCustomer customer = customerService.selectBCustomerById(customerId);
                if(customer != null){
                    memberCardResultVo.setPhonenumber(customer.getPhonenumber());
                }
            }
            //推荐员工信息
            Long recommendEmployeeId = memberCard.getRecommendEmployeeId();
            if(recommendEmployeeId != null){
                BEmployee recommendEmployee = employeeService.selectBEmployeeById(recommendEmployeeId);
                if(recommendEmployee != null){
                    memberCardResultVo.setRecommendEmployeeId(recommendEmployeeId);
                    memberCardResultVo.setRecommendEmployeeName(recommendEmployee.getEmployeeName());
                }
            }
        }
        return memberCardResultVo;
    }

    @Override
    public BMemberCardResultVo selectMembercardInfoByMemberInfo(BMemberCardQueryVo memberCardQueryVo) {
        Long customerId = memberCardQueryVo.getCustomerId();
        String phonenumber = memberCardQueryVo.getPhonenumber();
        if(customerId == null){
            if(StringUtils.isNotBlank(phonenumber)){
                BCustomer customer = customerService.selectByphonenumber(phonenumber);
                if(customer != null){
                    customerId = customer.getId();
                }
            }
        }
        BMemberCardResultVo resultVo = new BMemberCardResultVo();
        if(customerId != null){
            BMemberCard queryCondition = new BMemberCard();
            queryCondition.setCustomerId(customerId);
            List<BMemberCard> memberCards = this.selectBMemberCardList(queryCondition);
            if(CollectionUtils.isEmpty(memberCards)){
                return resultVo;
            }
            if(StringUtils.isEmpty(phonenumber)){
                BCustomer customer = customerService.selectBCustomerById(customerId);
                if(customer != null){
                    phonenumber = customer.getPhonenumber();
                }
            }
            if(memberCards.size() == 1){
                BeanUtils.copyBeanProp(resultVo,memberCards.get(0));
                resultVo.setPhonenumber(phonenumber);
                return resultVo;
            }
            if(memberCards.size() >1){
                BMemberCard maxRemainAmountMembercard = memberCards.get(0);
                BigDecimal maxRemainAmount = new BigDecimal(0);
                for(BMemberCard memberCard:memberCards){
                    BigDecimal remainAmount = memberCard.getRemainingAmount();
                    if(remainAmount.compareTo(maxRemainAmount) ==1){
                        maxRemainAmount = remainAmount;
                        maxRemainAmountMembercard = memberCard;
                    }
                }
                BeanUtils.copyBeanProp(resultVo,maxRemainAmountMembercard);
                resultVo.setPhonenumber(phonenumber);
                return resultVo;
            }
        }
        return null;
    }

    /**
     * 查询会员卡信息管理列表
     * 
     * @param bMemberCard 会员卡信息管理
     * @return 会员卡信息管理
     */
    @Override
    public List<BMemberCard> selectBMemberCardList(BMemberCard bMemberCard)
    {
        return bMemberCardMapper.selectBMemberCardList(bMemberCard);
    }

    @Override
    public List<BMemberCardResultVo> selectBMemberCardList(BMemberCardQueryVo bMemberCardQueryVo) {
        Long memberCardCustomerId = null;
        List<BMemberCardResultVo> resultVo = new LinkedList<>();
        List<BMemberCard> result = new LinkedList<>();
        //先根据会员id查询会员卡信息
        Long customerId = bMemberCardQueryVo.getCustomerId();
        if(customerId != null){
            BCustomer customer = customerService.selectBCustomerById(customerId);
            if(customer != null){
                BMemberCard bMemberCard = new BMemberCard();
                bMemberCard.setCustomerId(customerId);
                result = selectBMemberCardList(bMemberCard);
                if(CollectionUtils.isNotEmpty(result)){
                    for(BMemberCard memberCard:result){
                        BMemberCardResultVo memberCardResultVo = new BMemberCardResultVo();
                        //属性复制
                        BeanUtils.copyBeanProp(memberCardResultVo,memberCard);
                        memberCardResultVo.setPhonenumber(customer.getPhonenumber());
                        resultVo.add(memberCardResultVo);
                    }
                    return resultVo;
                }
                memberCardCustomerId = customerId;
            }
        }
        //根据会员手机号查询会员信息
        String phonenumber = bMemberCardQueryVo.getPhonenumber();
        if(StringUtils.isNotEmpty(phonenumber)){
            BCustomer customer = customerService.selectByphonenumber(phonenumber);
            if(customer != null){
                //根据会员信息查询会员卡信息
                Long dbcustomerId = customer.getId();
                if(dbcustomerId != null){
                    BMemberCard bMemberCard = new BMemberCard();
                    bMemberCard.setCustomerId(dbcustomerId);
                    result = selectBMemberCardList(bMemberCard);
                    if(CollectionUtils.isNotEmpty(result)){
                        for(BMemberCard memberCard:result){
                            BMemberCardResultVo memberCardResultVo = new BMemberCardResultVo();
                            //属性复制
                            BeanUtils.copyBeanProp(memberCardResultVo,memberCard);
                            memberCardResultVo.setPhonenumber(customer.getPhonenumber());
                            resultVo.add(memberCardResultVo);
                        }
                        return resultVo;
                    }
                    memberCardCustomerId = dbcustomerId;
                }
            }
        }

        //如果查不到会员卡是否创建
        boolean ifCreateCard = bMemberCardQueryVo.isIfCreateCard();
        if(ifCreateCard){
            if(memberCardCustomerId != null){
                BigDecimal zero = new BigDecimal(0);
                BMemberCard bMemberCard = new BMemberCard();
                bMemberCard.setCustomerId(memberCardCustomerId);

                bMemberCard.setCardno(IdUtils.simpleUUID());
                if(StringUtils.isNotEmpty(phonenumber)){
                    bMemberCard.setCardSecret(phonenumber.substring(phonenumber.length()-4));
                }else{
                    bMemberCard.setCardSecret("0000");
                }
                bMemberCard.setInitAmount(zero);
                bMemberCard.setRemainingAmount(zero);
                this.insertBMemberCard(bMemberCard);
                //将新建的会员卡添加到返回列表
                result.add(bMemberCard);
                for(BMemberCard memberCard:result){
                    BMemberCardResultVo memberCardResultVo = new BMemberCardResultVo();
                    //属性复制
                    BeanUtils.copyBeanProp(memberCardResultVo,memberCard);
                    memberCardResultVo.setPhonenumber(phonenumber);
                    resultVo.add(memberCardResultVo);
                }
            }
        }
        if(CollectionUtils.isEmpty(result)){
            BMemberCard bMemberCard = new BMemberCard();
            result = selectBMemberCardList(bMemberCard);
            for(BMemberCard memberCard:result){
                BMemberCardResultVo memberCardResultVo = new BMemberCardResultVo();
                //属性复制
                BeanUtils.copyBeanProp(memberCardResultVo,memberCard);

                Long thisCustomerId = memberCard.getCustomerId();
                if(thisCustomerId != null){
                    BCustomer customer = customerService.selectBCustomerById(thisCustomerId);
                    if(customer !=null){
                        memberCardResultVo.setPhonenumber(customer.getPhonenumber());
                    }
                }
                //推荐员工信息
                Long recommendEmployeeId = memberCard.getRecommendEmployeeId();
                if(recommendEmployeeId != null){
                    BEmployee recommendEmployee = employeeService.selectBEmployeeById(recommendEmployeeId);
                    if(recommendEmployee != null){
                        memberCardResultVo.setRecommendEmployeeId(recommendEmployeeId);
                        memberCardResultVo.setRecommendEmployeeName(recommendEmployee.getEmployeeName());
                    }
                }
                resultVo.add(memberCardResultVo);
            }
        }
        return resultVo;
    }

    /**
     * 新增会员卡信息管理
     * 
     * @param bMemberCard 会员卡信息管理
     * @return 结果
     */
    @Override
    public int insertBMemberCard(BMemberCard bMemberCard)
    {
        bMemberCard.setCreateTime(DateUtils.getNowDate());
        return bMemberCardMapper.insertBMemberCard(bMemberCard);
    }

    /**
     * 修改会员卡信息管理
     * 
     * @param bMemberCard 会员卡信息管理
     * @return 结果
     */
    @Override
    public int updateBMemberCard(BMemberCard bMemberCard)
    {
        if(bMemberCard == null){
            return 0;
        }
        bMemberCard.setUpdateTime(DateUtils.getNowDate());
        return bMemberCardMapper.updateBMemberCard(bMemberCard);
    }

    /**
     * 会员卡充值
     * @param memberCardRechargeVo
     * @return
     */
    @Override
    public int recharge(BMemberCardRechargeVo memberCardRechargeVo) {
        Long memberCardId = memberCardRechargeVo.getId();
        if(memberCardId == null){
            return  0;
        }
        BMemberCard rechargeMemberCard = this.selectBMemberCardById(memberCardId);
        if(rechargeMemberCard == null){
            return 0;
        }
        //当前余额
        BigDecimal remainingAmount = rechargeMemberCard.getRemainingAmount();
        if(remainingAmount == null){
            remainingAmount = new BigDecimal(0);
        }
        //本次充值金额
        String rechargeAmount = memberCardRechargeVo.getRechargeAmount();
        if(StringUtils.isBlank(rechargeAmount)){
            log.error("本次充值金额为空，跳过{}",rechargeAmount);
            return 0;
        }
        //去除空白符
        rechargeAmount = rechargeAmount.trim();
        if(!NumberUtils.isNumber(rechargeAmount)){
            log.error("本次充值金额不是数字，操作非法:{}",rechargeAmount);
            return 0;
        }
        //充值后金额=当前余额+本次充值金额
        BigDecimal newRemainingAmount = remainingAmount.add(new BigDecimal(rechargeAmount));
        rechargeMemberCard.setRemainingAmount(newRemainingAmount);

        Date now = new Date();
        //更新上次充值时间=现在
        rechargeMemberCard.setLastRechargeTime(now);
        return this.updateBMemberCard(rechargeMemberCard);
    }

    /**
     * 批量删除会员卡信息管理
     * 
     * @param ids 需要删除的会员卡信息管理主键
     * @return 结果
     */
    @Override
    public int deleteBMemberCardByIds(Long[] ids)
    {
        return bMemberCardMapper.deleteBMemberCardByIds(ids);
    }

    /**
     * 删除会员卡信息管理信息
     * 
     * @param id 会员卡信息管理主键
     * @return 结果
     */
    @Override
    public int deleteBMemberCardById(Long id)
    {
        return bMemberCardMapper.deleteBMemberCardById(id);
    }
}
