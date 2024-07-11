package com.hewei.member.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSONObject;
import com.hewei.member.membercard.domain.BMemberCard;
import com.hewei.member.vo.BCustomerQueryVo;
import com.hewei.member.vo.BCustomerResultVo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hewei.member.mapper.BCustomerMapper;
import com.hewei.member.domain.BCustomer;
import com.hewei.member.service.IBCustomerService;

/**
 * 会员信息管理Service业务层处理
 * 
 * @author hewei
 * @date 2024-06-04
 */
@Service
public class BCustomerServiceImpl implements IBCustomerService 
{
    @Autowired
    private BCustomerMapper bCustomerMapper;

    /**
     * 查询会员信息管理
     * 
     * @param id 会员信息管理主键
     * @return 会员信息管理
     */
    @Override
    public BCustomer selectBCustomerById(Long id)
    {
        return bCustomerMapper.selectBCustomerById(id);
    }

    /**
     * 查询会员信息+会员卡信息
     * @param queryVo
     * @return
     */
    @Override
    public List<BCustomerResultVo> selectBCustomerWithMemberCard(BCustomerQueryVo queryVo) {
        //字段对比信息，json串
//        String fieldCompare = queryVo.getFieldCompare();
//        if(StringUtils.isNotBlank(fieldCompare)){
//            Map<String,Object> fieldCompareMap = JSONObject.parseObject(fieldCompare);
//            queryVo.setFieldCompareMap(fieldCompareMap);
//        }
        //上次到店间隔(天)
        Integer lastCostTimeGap = queryVo.getLastCostTimeGap();
        if(lastCostTimeGap != null){
            //注意前面的负号，表示从现在减去相应的天数
            Date lastCostDateTime = DateUtils.addDays(DateUtils.getNowDate(),-lastCostTimeGap);
            queryVo.setLastCostDateTime(lastCostDateTime);
            //大小与转换，方便查询
            Map<String,Object> fieldCompareMap  = queryVo.getFieldCompareMap();
            String lastCostTimeGapStr = (String) fieldCompareMap.get("lastCostTimeGap");
            if("<=".equals(lastCostTimeGapStr)){
                fieldCompareMap.put("lastCostDateTime",">=");
            }else if(">=".equals(lastCostTimeGapStr)){
                fieldCompareMap.put("lastCostDateTime","<=");
            }
        }
        List<BCustomerResultVo> queryResult = bCustomerMapper.selectForMarketing(queryVo);
        if(CollectionUtils.isNotEmpty(queryResult)){
            for(BCustomerResultVo resultVo:queryResult){
                BMemberCard memberCard = new BMemberCard();
                memberCard.setRemainingAmount(resultVo.getRemainingAmount());
                resultVo.setMemberCard(memberCard);
            }
        }
        return queryResult;
    }

    /**
     * 查询会员信息管理列表
     * 
     * @param bCustomer 会员信息管理
     * @return 会员信息管理
     */
    @Override
    public List<BCustomer> selectBCustomerList(BCustomer bCustomer)
    {
        return bCustomerMapper.selectBCustomerList(bCustomer);
    }

    @Override
    public List<BCustomer> selectBCustomerByIds(List<Long> customerIds) {
        if(CollectionUtils.isEmpty(customerIds)){
            return new ArrayList<>();
        }
        return bCustomerMapper.selectBCustomerByIds(customerIds);
    }

    /**
     * 新增会员信息管理
     * 
     * @param bCustomer 会员信息管理
     * @return 结果
     */
    @Override
    public int insertBCustomer(BCustomer bCustomer)
    {
        bCustomer.setCreateTime(DateUtils.getNowDate());
        return bCustomerMapper.insertBCustomer(bCustomer);
    }

    /**
     * 修改会员信息管理
     * 
     * @param bCustomer 会员信息管理
     * @return 结果
     */
    @Override
    public int updateBCustomer(BCustomer bCustomer)
    {
        bCustomer.setUpdateTime(DateUtils.getNowDate());
        return bCustomerMapper.updateBCustomer(bCustomer);
    }

    /**
     * 批量删除会员信息管理
     * 
     * @param ids 需要删除的会员信息管理主键
     * @return 结果
     */
    @Override
    public int deleteBCustomerByIds(Long[] ids)
    {
        return bCustomerMapper.deleteBCustomerByIds(ids);
    }

    /**
     * 删除会员信息管理信息
     * 
     * @param id 会员信息管理主键
     * @return 结果
     */
    @Override
    public int deleteBCustomerById(Long id)
    {
        return bCustomerMapper.deleteBCustomerById(id);
    }

    @Override
    public BCustomer selectByphonenumber(String phonenumber) {
        BCustomer bCustomer = new BCustomer();
        bCustomer.setPhonenumber(phonenumber);
        List<BCustomer> customers = this.selectBCustomerList(bCustomer);
        if(CollectionUtils.isNotEmpty(customers)){
            return customers.get(0);
        }
        return null;
    }
}
