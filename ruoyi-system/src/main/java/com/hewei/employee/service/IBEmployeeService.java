package com.hewei.employee.service;

import java.util.List;
import com.hewei.employee.domain.BEmployee;

/**
 * 员工信息管理Service接口
 * 
 * @author hewei
 * @date 2024-06-04
 */
public interface IBEmployeeService 
{
    /**
     * 查询员工信息管理
     * 
     * @param id 员工信息管理主键
     * @return 员工信息管理
     */
    public BEmployee selectBEmployeeById(Long id);
    BEmployee selectByNameOrWorkno(String nameOrWorkno);

    /**
     * 查询员工信息管理列表
     * 
     * @param bEmployee 员工信息管理
     * @return 员工信息管理集合
     */
    public List<BEmployee> selectBEmployeeList(BEmployee bEmployee);
    List<BEmployee> selectBEmployeeByIds(List<Long> employeeIds);
    /**
     * 新增员工信息管理
     * 
     * @param bEmployee 员工信息管理
     * @return 结果
     */
    public int insertBEmployee(BEmployee bEmployee);

    /**
     * 修改员工信息管理
     * 
     * @param bEmployee 员工信息管理
     * @return 结果
     */
    public int updateBEmployee(BEmployee bEmployee);

    /**
     * 批量删除员工信息管理
     * 
     * @param ids 需要删除的员工信息管理主键集合
     * @return 结果
     */
    public int deleteBEmployeeByIds(Long[] ids);

    /**
     * 删除员工信息管理信息
     * 
     * @param id 员工信息管理主键
     * @return 结果
     */
    public int deleteBEmployeeById(Long id);
}
