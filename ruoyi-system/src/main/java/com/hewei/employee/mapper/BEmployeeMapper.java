package com.hewei.employee.mapper;

import java.util.List;
import com.hewei.employee.domain.BEmployee;
import org.apache.ibatis.annotations.Param;

/**
 * 员工信息管理Mapper接口
 * 
 * @author hewei
 * @date 2024-06-04
 */
public interface BEmployeeMapper 
{
    /**
     * 查询员工信息管理
     * 
     * @param id 员工信息管理主键
     * @return 员工信息管理
     */
    public BEmployee selectBEmployeeById(Long id);

    /**
     * 查询员工信息管理列表
     * 
     * @param bEmployee 员工信息管理
     * @return 员工信息管理集合
     */
    public List<BEmployee> selectBEmployeeList(BEmployee bEmployee);

    /**
     * 根据员工姓名或工号查询
     * @param nameOrWorkno 员工姓名或工号
     * @return
     */
    List<BEmployee> selectByNameOrWorkno(@Param("nameOrWorkno") String nameOrWorkno);
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
     * 删除员工信息管理
     * 
     * @param id 员工信息管理主键
     * @return 结果
     */
    public int deleteBEmployeeById(Long id);

    /**
     * 批量删除员工信息管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBEmployeeByIds(Long[] ids);


    List<BEmployee> selectBEmployeeByIds(@Param("employeeIds") List<Long> employeeIds);
}
