package com.hewei.member.service;

import java.util.List;
import com.hewei.member.domain.BCustomer;
import com.hewei.member.vo.BCustomerQueryVo;
import com.hewei.member.vo.BCustomerResultVo;

/**
 * 会员信息管理Service接口
 * 
 * @author hewei
 * @date 2024-06-04
 */
public interface IBCustomerService 
{
    /**
     * 查询会员信息管理
     * 
     * @param id 会员信息管理主键
     * @return 会员信息管理
     */
    BCustomer selectBCustomerById(Long id);

    List<BCustomerResultVo> selectBCustomerWithMemberCard(BCustomerQueryVo queryVo);

    /**
     * 查询会员信息管理列表
     * 
     * @param bCustomer 会员信息管理
     * @return 会员信息管理集合
     */
    public List<BCustomer> selectBCustomerList(BCustomer bCustomer);
    List<BCustomer> selectBCustomerByIds(List<Long> customerIds);
    /**
     * 新增会员信息管理
     * 
     * @param bCustomer 会员信息管理
     * @return 结果
     */
    public int insertBCustomer(BCustomer bCustomer);

    /**
     * 修改会员信息管理
     * 
     * @param bCustomer 会员信息管理
     * @return 结果
     */
    public int updateBCustomer(BCustomer bCustomer);

    /**
     * 批量删除会员信息管理
     * 
     * @param ids 需要删除的会员信息管理主键集合
     * @return 结果
     */
    public int deleteBCustomerByIds(Long[] ids);

    /**
     * 删除会员信息管理信息
     * 
     * @param id 会员信息管理主键
     * @return 结果
     */
    public int deleteBCustomerById(Long id);

    BCustomer selectByphonenumber(String phonenumber);


}
