package com.hewei.employee.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hewei.employee.mapper.BEmployeeMapper;
import com.hewei.employee.domain.BEmployee;
import com.hewei.employee.service.IBEmployeeService;

/**
 * 员工信息管理Service业务层处理
 * 
 * @author hewei
 * @date 2024-06-04
 */
@Service
public class BEmployeeServiceImpl implements IBEmployeeService 
{
    @Autowired
    private BEmployeeMapper bEmployeeMapper;

    /**
     * 查询员工信息管理
     * 
     * @param id 员工信息管理主键
     * @return 员工信息管理
     */
    @Override
    public BEmployee selectBEmployeeById(Long id)
    {
        return bEmployeeMapper.selectBEmployeeById(id);
    }

    /**
     * 根据员工姓名或工号查询
     * @param nameOrWorkno
     * @return
     */
    @Override
    public BEmployee selectByNameOrWorkno(String nameOrWorkno) {
        List<BEmployee> employees = bEmployeeMapper.selectByNameOrWorkno(nameOrWorkno);
        if(CollectionUtils.isNotEmpty(employees)){
            return employees.get(0);
        }
        return null;
    }

    /**
     * 查询员工信息管理列表
     * 
     * @param bEmployee 员工信息管理
     * @return 员工信息管理
     */
    @Override
    public List<BEmployee> selectBEmployeeList(BEmployee bEmployee)
    {
        return bEmployeeMapper.selectBEmployeeList(bEmployee);
    }

    @Override
    public List<BEmployee> selectBEmployeeByIds(List<Long> employeeIds) {
        if(CollectionUtils.isEmpty(employeeIds)){
            return new ArrayList<>();
        }
        return bEmployeeMapper.selectBEmployeeByIds(employeeIds);
    }

    /**
     * 新增员工信息管理
     * 
     * @param bEmployee 员工信息管理
     * @return 结果
     */
    @Override
    public int insertBEmployee(BEmployee bEmployee)
    {
        bEmployee.setCreateTime(DateUtils.getNowDate());
        return bEmployeeMapper.insertBEmployee(bEmployee);
    }

    /**
     * 修改员工信息管理
     * 
     * @param bEmployee 员工信息管理
     * @return 结果
     */
    @Override
    public int updateBEmployee(BEmployee bEmployee)
    {
        bEmployee.setUpdateTime(DateUtils.getNowDate());
        return bEmployeeMapper.updateBEmployee(bEmployee);
    }

    /**
     * 批量删除员工信息管理
     * 
     * @param ids 需要删除的员工信息管理主键
     * @return 结果
     */
    @Override
    public int deleteBEmployeeByIds(Long[] ids)
    {
        return bEmployeeMapper.deleteBEmployeeByIds(ids);
    }

    /**
     * 删除员工信息管理信息
     * 
     * @param id 员工信息管理主键
     * @return 结果
     */
    @Override
    public int deleteBEmployeeById(Long id)
    {
        return bEmployeeMapper.deleteBEmployeeById(id);
    }
}
