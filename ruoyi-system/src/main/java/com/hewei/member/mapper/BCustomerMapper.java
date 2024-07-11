package com.hewei.member.mapper;

import java.util.List;
import com.hewei.member.domain.BCustomer;
import com.hewei.member.vo.BCustomerQueryVo;
import com.hewei.member.vo.BCustomerResultVo;
import org.apache.ibatis.annotations.Param;

/**
 * 会员信息管理Mapper接口
 * 
 * @author hewei
 * @date 2024-06-04
 */
public interface BCustomerMapper 
{
    /**
     * 查询会员信息管理
     * 
     * @param id 会员信息管理主键
     * @return 会员信息管理
     */
    public BCustomer selectBCustomerById(Long id);

    /**
     * 查询会员信息管理列表
     * 
     * @param bCustomer 会员信息管理
     * @return 会员信息管理集合
     */
    public List<BCustomer> selectBCustomerList(BCustomer bCustomer);
    List<BCustomer> selectBCustomerByIds(@Param("customerIds") List<Long> customerIds);
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
     * 删除会员信息管理
     * 
     * @param id 会员信息管理主键
     * @return 结果
     */
    public int deleteBCustomerById(Long id);

    /**
     * 批量删除会员信息管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBCustomerByIds(Long[] ids);

    /**
     * 为营销管理功能进行的查询
     * @param queryVo
     * @return
     */
    List<BCustomerResultVo> selectForMarketing(BCustomerQueryVo queryVo);
}
