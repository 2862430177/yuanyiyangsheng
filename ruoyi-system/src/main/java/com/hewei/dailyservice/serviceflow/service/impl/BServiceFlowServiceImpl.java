package com.hewei.dailyservice.serviceflow.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.hewei.common.constant.DictConstants;
import com.hewei.dailyservice.serviceflow.vo.BServiceFlowQueryVo;
import com.hewei.dailyservice.serviceflow.vo.BServiceFlowResultVo;
import com.hewei.dailyservice.serviceflow.vo.BServiceFlowSettleVo;
import com.hewei.employee.domain.BEmployee;
import com.hewei.employee.service.IBEmployeeService;
import com.hewei.member.domain.BCustomer;
import com.hewei.member.membercard.domain.BMemberCard;
import com.hewei.member.membercard.service.IBMemberCardService;
import com.hewei.member.service.IBCustomerService;
import com.hewei.shop.equipment.domain.BRoomEquipment;
import com.hewei.shop.equipment.service.IBRoomEquipmentService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.hewei.dailyservice.serviceflow.domain.BServiceFlowDetail;
import com.hewei.dailyservice.serviceflow.mapper.BServiceFlowMapper;
import com.hewei.dailyservice.serviceflow.domain.BServiceFlow;
import com.hewei.dailyservice.serviceflow.service.IBServiceFlowService;

/**
 * 服务流水,一次服务一条流水记录Service业务层处理
 * 
 * @author hewei
 * @date 2024-06-05
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BServiceFlowServiceImpl implements IBServiceFlowService 
{

    private final BServiceFlowMapper bServiceFlowMapper;
    private final IBEmployeeService employeeService;
    private final IBCustomerService customerService;
    private final IBRoomEquipmentService roomEquipmentService;

    private final IBMemberCardService memberCardService;


    /**
     * 查询服务流水,一次服务一条流水记录
     * 
     * @param id 服务流水,一次服务一条流水记录主键
     * @return 服务流水,一次服务一条流水记录
     */
    @Override
    public BServiceFlow selectBServiceFlowById(Long id)
    {
        return bServiceFlowMapper.selectBServiceFlowById(id);
    }

    /**
     * 查询服务流水,一次服务一条流水记录列表
     * 
     * @param bServiceFlow 服务流水,一次服务一条流水记录
     * @return 服务流水,一次服务一条流水记录
     */
    @Override
    public List<BServiceFlow> selectBServiceFlowList(BServiceFlow bServiceFlow)
    {
        return bServiceFlowMapper.selectBServiceFlowList(bServiceFlow);
    }

    @Override
    public List<BServiceFlowResultVo> selectBServiceFlowList(BServiceFlowQueryVo serviceFlowQueryVo) {
        //服务流水需要的查询条件(员工id、会员id)
        Long employeeId = null;
        Long customerId = null;
        //查询条件(服务员工姓名或工号)
        String nameOrWorkno = serviceFlowQueryVo.getNameOrWorkno();
        if(StringUtils.isNotBlank(nameOrWorkno)){
            BEmployee employee = employeeService.selectByNameOrWorkno(nameOrWorkno);
            if(employee != null){
                employeeId = employee.getId();
            }
        }
        //查询条件(会员手机号)
        String phonenumber = serviceFlowQueryVo.getPhonenumber();
        if(StringUtils.isNotBlank(phonenumber)){
            BCustomer customer = customerService.selectByphonenumber(phonenumber);
            if(customer != null){
                customerId = customer.getId();
            }
        }
        BServiceFlow queryCondition = new BServiceFlow();
        queryCondition.setEmployeeId(employeeId);
        queryCondition.setCustomerId(customerId);
        queryCondition.setStatus(serviceFlowQueryVo.getStatus());
        queryCondition.setCreateTime(serviceFlowQueryVo.getCreateTime());
        List<BServiceFlow> result = this.selectBServiceFlowList(queryCondition);

        List<BServiceFlowResultVo> resultVoList = new LinkedList<>();
        if(CollectionUtils.isNotEmpty(result)){
            //员工ids
            List<Long> employeeIds = new LinkedList<>();
            //设备ids
            List<Long> emquipmentIds = new LinkedList<>();
            //会员ids
            List<Long> customerIds = new LinkedList<>();
            for(BServiceFlow serviceFlow:result){
                BServiceFlowResultVo serviceFlowResultVo = new BServiceFlowResultVo();
                BeanUtils.copyBeanProp(serviceFlowResultVo,serviceFlow);
                resultVoList.add(serviceFlowResultVo);

                Long thisEmployeeId = serviceFlow.getEmployeeId();
                Long thisEmquipmentId = serviceFlow.getEquipmentId();
                Long thisCustomerId = serviceFlow.getCustomerId();
                if(thisEmployeeId != null){
                    employeeIds.add(thisEmployeeId);
                }
                if(thisEmquipmentId != null){
                    emquipmentIds.add(thisEmquipmentId);
                }
                if(thisCustomerId != null){
                    customerIds.add(thisCustomerId);
                }
            }
            List<BEmployee> employeeList = employeeService.selectBEmployeeByIds(employeeIds);
            List<BRoomEquipment> roomEquipmentList = roomEquipmentService.selectBRoomEquipmentByIds(emquipmentIds);
            List<BCustomer> customerList = customerService.selectBCustomerByIds(customerIds);

            Map<Long, BEmployee> employeeMap = employeeList.stream()
                    .collect(Collectors.toMap(BEmployee::getId, employee -> employee, (existing, replacement) -> existing));
            Map<Long, BRoomEquipment> roomEquipmentMap = roomEquipmentList.stream()
                    .collect(Collectors.toMap(BRoomEquipment::getId, roomEquipment -> roomEquipment, (existing, replacement) -> existing));
            Map<Long, BCustomer> customerMap = customerList.stream()
                    .collect(Collectors.toMap(BCustomer::getId, customer -> customer, (existing, replacement) -> existing));
            for(BServiceFlowResultVo resultVo :resultVoList){
                Long thisemployeeId = resultVo.getEmployeeId();
                BEmployee employee = employeeMap.get(thisemployeeId);
                if(employee != null){
                    resultVo.setEmployeeName(employee.getEmployeeName());
                }
                Long thisEmquipmentId = resultVo.getEquipmentId();
                BRoomEquipment roomEquipment = roomEquipmentMap.get(thisEmquipmentId);
                if(roomEquipment != null){
                    resultVo.setEquipmentName(roomEquipment.getEquipmentDesc());
                }
                Long thisCustomerId = resultVo.getCustomerId();
                BCustomer customer = customerMap.get(thisCustomerId);
                if(customer != null){
                    resultVo.setCustomerName(customer.getCustomerName());
                    resultVo.setPhonenumber(customer.getPhonenumber());
                }
            }
        }
        return resultVoList;
    }

    /**
     * 新增服务流水,一次服务一条流水记录
     * 
     * @param bServiceFlow 服务流水,一次服务一条流水记录
     * @return 结果
     */
    @Transactional
    @Override
    public int insertBServiceFlow(BServiceFlow bServiceFlow)
    {
        bServiceFlow.setCreateTime(DateUtils.getNowDate());
        //初始化状态为服务中
        bServiceFlow.setStatus(DictConstants.settlementStatus_inprocess);
        int rows = bServiceFlowMapper.insertBServiceFlow(bServiceFlow);
        insertBServiceFlowDetail(bServiceFlow);
        //更新设备开始使用时间
        Long equipmentId = bServiceFlow.getEquipmentId();
        if(equipmentId != null){
            BRoomEquipment roomEquipment = roomEquipmentService.selectBRoomEquipmentById(equipmentId);
            if(roomEquipment != null){
                Date now = new Date();
                roomEquipment.setStartUseTime(now);
                roomEquipment.setStatus(DictConstants.equipmentUseStatus_using);
                roomEquipmentService.updateBRoomEquipment(roomEquipment);
            }
        }
        //更新员工状态为工作中
        Long employeeId = bServiceFlow.getEmployeeId();
        if(employeeId != null){
            BEmployee employee = employeeService.selectBEmployeeById(employeeId);
            if(employee != null){
                employee.setWorkingStatus(DictConstants.workingStatus_working);
                employeeService.updateBEmployee(employee);
            }
        }
        return rows;
    }

    /**
     * 结算
     * @param serviceFlowSettleVo
     * @return
     */
    @Transactional
    @Override
    public AjaxResult settle(BServiceFlowSettleVo serviceFlowSettleVo) {
        AjaxResult result = new AjaxResult();
        //获取页面服务总收费
        BigDecimal serviceCharge = serviceFlowSettleVo.getServiceCharge();
        if(serviceCharge == null){
            serviceCharge = new BigDecimal(0);
        }
        //获取服务项明细总额 = 服务项数量*服务项单价
        BigDecimal detailTotalAmount = new BigDecimal(0);
        List<BServiceFlowDetail> bServiceFlowDetailList = serviceFlowSettleVo.getBServiceFlowDetailList();
        if(CollectionUtils.isNotEmpty(bServiceFlowDetailList)){
            for(BServiceFlowDetail serviceFlowDetail: bServiceFlowDetailList){
                Long detailNum = serviceFlowDetail.getDetailNum();
                if(detailNum == null){
                    detailNum = 1l;
                }
                BigDecimal detailUnitPrice = serviceFlowDetail.getDetailUnitPrice();
                if(detailUnitPrice == null){
                    detailUnitPrice = new BigDecimal(0);
                }
                BigDecimal detailPrice = detailUnitPrice.multiply(new BigDecimal(detailNum));
                detailTotalAmount.add(detailPrice);
            }
        }
        //比较总收费与明细总额，不相同打印警告日期
        if(serviceCharge.compareTo(detailTotalAmount) !=0){
            log.warn("总收费与明细总额不等，总收费:{},明细总额:{}",serviceCharge,detailTotalAmount);
        }
        //以页面传值为准，记录服务总收费、服务明细等数据
        Long id = serviceFlowSettleVo.getId();
        BServiceFlow serviceFlow = this.selectBServiceFlowById(id);
        if(serviceFlow == null){
            return AjaxResult.error("服务单不存在,请联系管理员："+id);
        }
        String status = serviceFlow.getStatus();
        if(DictConstants.settlementStatus_settled.equals(status)||
        DictConstants.settlementStatus_canceled.equals(status)||
        DictConstants.settlementStatus_ended.equals(status)||
        DictConstants.settlementStatus_hasReSettled.equals(status)){
            return AjaxResult.error("只有状态为服务中的服务单，才可以结算，当前状态"+status);
        }

        BeanUtils.copyBeanProp(serviceFlow,serviceFlowSettleVo);
        //服务项目和产品明细
        List<BServiceFlowDetail> serviceFlowDetails = serviceFlowSettleVo.getBServiceFlowDetailList();
        serviceFlow.setBServiceFlowDetailList(serviceFlowDetails);
        //如果是会员卡结算，校验会员卡余额
        String settlementType = serviceFlowSettleVo.getSettlementType();
        String phonenumber = serviceFlowSettleVo.getPhonenumber();
        BMemberCard memberCard = null;
        if(DictConstants.settlementType_membercard.equals(settlementType)){
            Long memberCardId = serviceFlowSettleVo.getMemberCardId();
            if(memberCardId != null){
                memberCard = memberCardService.selectBMemberCardById(memberCardId);
                if(memberCard != null){
                    BigDecimal remainingAmount = memberCard.getRemainingAmount();
                    //如果金额不足，保存草稿,提示前台会员会员卡余额不足，请充值，结算失败
                    if(serviceCharge.compareTo(remainingAmount)==1){
                        //不修改结算状态，只保存草稿
                        String errorTip = "会员卡余额不足，请充值，会员手机号："+phonenumber;
                        result = AjaxResult.error(errorTip);
                    }else{
                        //如果金额足够，扣除会员卡余额，结算成功
                        //会员卡余额=会员卡余额-本次总收费
                        memberCard.setRemainingAmount(remainingAmount.subtract(serviceCharge));
                        //更新会员卡最后使用时间，余额
                        memberCard.setLastCostTime(new Date());
                        //设置结算状态为已结算
                        serviceFlow.setStatus(DictConstants.settlementStatus_settled);
                    }
                }
            }
        }else if(DictConstants.settlementType_cash.equals(settlementType)){
            //现金结算,不做会员卡相关操作
            //设置结算状态为已结算
            serviceFlow.setStatus(DictConstants.settlementStatus_settled);
        }
        //如果是会员，更新会员最后消费时间
        Long customerId = serviceFlowSettleVo.getCustomerId();
        if(customerId != null){
            BCustomer customer = customerService.selectBCustomerById(customerId);
            if(customer != null){
                customer.setLastUseDate(new Date());
                customerService.updateBCustomer(customer);
            }
        }
        //更新设备最后使用时间和持续时间,更新使用状态为未使用
        Long equipmentId = serviceFlowSettleVo.getEquipmentId();
        if(equipmentId != null){
            BRoomEquipment roomEquipment = roomEquipmentService.selectBRoomEquipmentById(equipmentId);
            if(roomEquipment != null){
                roomEquipment.setEndUseTime(new Date());
                Long continueUsageTime = DateUtils.timeDistanceMinute(roomEquipment.getEndUseTime(),roomEquipment.getStartUseTime());
                roomEquipment.setContinueUsageTime(continueUsageTime);
                roomEquipment.setStatus(DictConstants.equipmentUseStatus_unuse);
            }
        }
        //如果员工没有其他服务状态为服务中的服务单，更新员工状态为空闲中
        Long employeeId = serviceFlowSettleVo.getEmployeeId();
        if(employeeId != null){
            BEmployee employee = employeeService.selectBEmployeeById(employeeId);
            if(employee != null){
                BServiceFlow queryCondition = new BServiceFlow();
                queryCondition.setEmployeeId(employeeId);
                queryCondition.setStatus(DictConstants.settlementStatus_inprocess);
                List<BServiceFlow> inprocessServiceFlow = this.selectBServiceFlowList(queryCondition);
                if(CollectionUtils.isEmpty(inprocessServiceFlow)){
                    employee.setWorkingStatus(DictConstants.workingStatus_free);
                }
            }
        }
        //数据库操作集中处理，减少事务锁定数据库记录时间
        //更新会员卡余额
        memberCardService.updateBMemberCard(memberCard);
        //更新服务流水总金额及状态
        this.updateBServiceFlow(serviceFlow);
        if(result.isError()){
            return result;
        }
        return AjaxResult.success("结算成功");
    }

    /**
     * 重新结算
     * @param serviceFlowSettleVo
     * @return
     */
    @Transactional
    @Override
    public AjaxResult reSettle(BServiceFlowSettleVo serviceFlowSettleVo) {
        //原服务单id
        Long sourceId = serviceFlowSettleVo.getId();
        //新建服务单结算(新增)
        BServiceFlow bServiceFlow = new BServiceFlow();
        bServiceFlow.setEmployeeId(serviceFlowSettleVo.getEmployeeId());
        bServiceFlow.setEquipmentId(serviceFlowSettleVo.getEquipmentId());
        this.insertBServiceFlow(bServiceFlow);
        //赋值为新的服务单id
        Long newServiceFlowId = bServiceFlow.getId();
        serviceFlowSettleVo.setId(newServiceFlowId);
        List<BServiceFlowDetail> bServiceFlowDetailList = serviceFlowSettleVo.getBServiceFlowDetailList();
        if(CollectionUtils.isNotEmpty(bServiceFlowDetailList)){
            for(BServiceFlowDetail serviceFlowDetail:bServiceFlowDetailList){
                //去除主键，插入时自动重新生成
                serviceFlowDetail.setId(null);
                serviceFlowDetail.setFlowId(newServiceFlowId);
            }
        }
        AjaxResult result = this.settle(serviceFlowSettleVo);
        if(!result.isSuccess()){
            log.error("重新结算失败，直接返回错误："+result.get(AjaxResult.MSG_TAG));
            //删掉新创建的服务单
            this.deleteBServiceFlowById(newServiceFlowId);
            return result;
        }

        //回滚原服务单会员金额
        BServiceFlow sourceServiceFlow = this.selectBServiceFlowById(sourceId);
        //如果是会员卡结算，校验会员卡余额
        String settlementType = sourceServiceFlow.getSettlementType();
        BMemberCard memberCard = null;
        log.info("结算方式："+settlementType);
        if(DictConstants.settlementType_membercard.equals(settlementType)){
            Long memberCardId = sourceServiceFlow.getMemberCardId();
            BigDecimal serviceCharge = sourceServiceFlow.getServiceCharge();
            if(memberCardId != null){
                memberCard = memberCardService.selectBMemberCardById(memberCardId);
                if(memberCard != null){
                    BigDecimal remainingAmount = memberCard.getRemainingAmount();
                    //会员卡余额=会员卡余额+本次总收费
                    memberCard.setRemainingAmount(remainingAmount.add(serviceCharge));
                    //更新会员卡最后使用时间，余额
                    memberCard.setLastCostTime(new Date());
                }
            }
        }
        //修改原服务单为已重新结算
        sourceServiceFlow.setStatus(DictConstants.settlementStatus_hasReSettled);

        //统一更新数据库
        memberCardService.updateBMemberCard(memberCard);
        this.updateBServiceFlow(sourceServiceFlow);
        return AjaxResult.success("重新结算成功");
    }

    /**
     * 修改服务流水,一次服务一条流水记录
     * 
     * @param bServiceFlow 服务流水,一次服务一条流水记录
     * @return 结果
     */
    @Transactional
    @Override
    public int updateBServiceFlow(BServiceFlow bServiceFlow)
    {
        bServiceFlow.setUpdateTime(DateUtils.getNowDate());
        //删除服务明细重新插入
        bServiceFlowMapper.deleteBServiceFlowDetailByFlowId(bServiceFlow.getId());
        insertBServiceFlowDetail(bServiceFlow);
        return bServiceFlowMapper.updateBServiceFlow(bServiceFlow);
    }

    /**
     * 批量删除服务流水,一次服务一条流水记录
     * 
     * @param ids 需要删除的服务流水,一次服务一条流水记录主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteBServiceFlowByIds(Long[] ids)
    {
        bServiceFlowMapper.deleteBServiceFlowDetailByFlowIds(ids);
        return bServiceFlowMapper.deleteBServiceFlowByIds(ids);
    }

    /**
     * 删除服务流水,一次服务一条流水记录信息
     * 
     * @param id 服务流水,一次服务一条流水记录主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteBServiceFlowById(Long id)
    {
        bServiceFlowMapper.deleteBServiceFlowDetailByFlowId(id);
        return bServiceFlowMapper.deleteBServiceFlowById(id);
    }

    /**
     * 新增服务流水,一次服务一条流水记录信息
     * 
     * @param bServiceFlow 服务流水,一次服务一条流水记录对象
     */
    public void insertBServiceFlowDetail(BServiceFlow bServiceFlow)
    {
        List<BServiceFlowDetail> bServiceFlowDetailList = bServiceFlow.getBServiceFlowDetailList();
        Long id = bServiceFlow.getId();
        if (StringUtils.isNotNull(bServiceFlowDetailList))
        {
            List<BServiceFlowDetail> list = new ArrayList<BServiceFlowDetail>();
            for (BServiceFlowDetail bServiceFlowDetail : bServiceFlowDetailList)
            {
                bServiceFlowDetail.setFlowId(id);
                list.add(bServiceFlowDetail);
            }
            if (list.size() > 0)
            {
                bServiceFlowMapper.batchBServiceFlowDetail(list);
            }
        }
    }
}
