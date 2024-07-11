package com.hewei.operatemanage.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.hewei.employee.domain.BEmployee;
import com.hewei.employee.service.IBEmployeeService;
import com.hewei.member.membercard.domain.BMemberCard;
import com.hewei.member.membercard.service.IBMemberCardService;
import com.hewei.operatemanage.mapper.OperatemanageMapper;
import com.hewei.operatemanage.service.IOperatemanageService;
import com.hewei.operatemanage.vo.*;
import com.hewei.shop.serviceitems.domain.BServiceItems;
import com.hewei.shop.serviceitems.service.IBServiceItemsService;
import com.hewei.shop.serviceproducts.domain.BServiceProducts;
import com.hewei.shop.serviceproducts.service.IBServiceProductsService;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description 运营管理业务逻辑实现类
 * @Author hewei
 * @Date 2024/6/11 14:13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OperatemanageServiceImpl implements IOperatemanageService {

    private final OperatemanageMapper operatemanageMapper;
    private final IBEmployeeService employeeService;
    private final IBMemberCardService memberCardService;
    private final IBServiceItemsService itemsService;
    private final IBServiceProductsService productsService;
    private final ISysConfigService sysConfigService;

    @Override
    public TurnoverStatisticsResultVo getTurnoverStatisticsList(TurnoverStatisticsQueryVo turtuverStatisticsQueryVo) {
        //获取总金额（店铺维度）
        List<TurnoverStatisticsForDateResultVo> turnoverStatisticsByTotalList = operatemanageMapper.selectTurnoverStatisticsList(turtuverStatisticsQueryVo);
        //获取项目金额（店铺维度）
        List<TurnoverStatisticsForDateResultVo> turnoverStatisticsByItemList = operatemanageMapper.selectTurnoverStatisticsByItemList(turtuverStatisticsQueryVo);
        //获取产品金额（店铺维度）
        List<TurnoverStatisticsForDateResultVo> turnoverStatisticsByProductList = operatemanageMapper.selectTurnoverStatisticsByProductList(turtuverStatisticsQueryVo);
        //selectItemProductAmountByStore
        //汇总,总金额=项目金额+产品金额
        List<TurnoverStatisticsForDateResultVo> turnoverStatisticsForDateList = new LinkedList<>();
        turnoverStatisticsForDateList.addAll(turnoverStatisticsByTotalList);
        turnoverStatisticsForDateList.addAll(turnoverStatisticsByItemList);
        turnoverStatisticsForDateList.addAll(turnoverStatisticsByProductList);
        //返回结果各项
        Map<String,TurnoverStatisticsForDateResultVo> dateResultVoMap = new HashMap<>();
        BigDecimal totalItemAmount = new BigDecimal(0);
        BigDecimal totalProductAmount = new BigDecimal(0);
        BigDecimal totalServiceCharge = new BigDecimal(0);
        //根据相同日期合并TurnoverStatisticsForDateResultVo对象，同时转换为map
        for(TurnoverStatisticsForDateResultVo forDateResultVo:turnoverStatisticsForDateList){
            Date createTime = forDateResultVo.getCreateTime();
            String createTimeStr = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,createTime);
            BigDecimal itemAmount = forDateResultVo.getItemAmount();
            BigDecimal productAmount = forDateResultVo.getProductAmount();
            BigDecimal serviceCharge = forDateResultVo.getServiceCharge();

            TurnoverStatisticsForDateResultVo existResultVo = dateResultVoMap.get(createTimeStr);
            if(existResultVo == null){
                existResultVo = new TurnoverStatisticsForDateResultVo();
                existResultVo.setItemAmount(forDateResultVo.getItemAmount());
                existResultVo.setProductAmount(forDateResultVo.getProductAmount());
                existResultVo.setServiceCharge(forDateResultVo.getServiceCharge());
                dateResultVoMap.put(createTimeStr,existResultVo);
            }else{
                //合并，求和，已确定唯一，只取有值 TODO
                if(itemAmount != null){
                    existResultVo.setItemAmount(itemAmount);
                }
                if(productAmount != null){
                    existResultVo.setProductAmount(productAmount);
                }
                if(serviceCharge != null){
                    existResultVo.setServiceCharge(serviceCharge);
                }
            }
            //汇总
            if(itemAmount != null){
                totalItemAmount = totalItemAmount.add(itemAmount);
            }
            if(productAmount != null){
                totalProductAmount = totalProductAmount.add(productAmount);
            }
            if(serviceCharge != null){
                totalServiceCharge = totalServiceCharge.add(serviceCharge);
            }
        }
        TurnoverStatisticsResultVo turnoverStatisticsResultVo = new TurnoverStatisticsResultVo();
        turnoverStatisticsResultVo.setDateResultVoMap(dateResultVoMap);
        turnoverStatisticsResultVo.setTotalItemAmount(totalItemAmount.toPlainString());
        turnoverStatisticsResultVo.setTotalProductAmount(totalProductAmount.toPlainString());
        turnoverStatisticsResultVo.setTotalServiceCharge(totalServiceCharge.toPlainString());
        return turnoverStatisticsResultVo;
    }

    @Override
    public List<EmployeePerformanceStatisticsResultVo> getEmployeePerformanceStatisticsList(EmployeePerformanceStatisticsQueryVo queryVo) {
        //获取所有员工
        BEmployee employeeQueryCondition = new BEmployee();
        employeeQueryCondition.setId(queryVo.getEmployeeId());
        List<BEmployee> employeeList = employeeService.selectBEmployeeList(employeeQueryCondition);
        Map<Long,String> employeeIdNameMap = this.convertEmployeeList(employeeList);
        //获取实际消费金额
        List<EmployeePerformanceindicatorResultVo> actualServicechargeList = operatemanageMapper.selectActualServicecharge(queryVo);
        Map<Long,Map<String, EmployeePerformanceindicatorResultVo>> actualServicechargeMap = this.handleActualServicechargeList(actualServicechargeList);
        //获取项目金额、产品金额
        //获取服务分类(项目|产品)，服务id(项目id|产品id)，项目金额，产品金额，项目数量、产品数量
        List<Map<String,Object>> itemProductAmountList = operatemanageMapper.selectItemProductAmount(queryVo);
        Map<Long,Map<String,EmployeePerformanceindicatorResultVo>> itemProductAmountMap = this.handleItemProductAmountList(itemProductAmountList);
        //获取会员办卡金额(首次充值金额)
        List<Map<String,Object>> membercardFirstList = operatemanageMapper.selectMemberCardFirstAmnout(queryVo);
        Map<Long,Map<String,EmployeePerformanceindicatorResultVo>> membercardFirstMap = this.handleMembercardFirstList(membercardFirstList);
        //获取会员充值金额(续卡金额)<日期，<会员卡id,充值金额>>
        Map<String,Map<Long, BigDecimal>> membercardRechargeAmountList = this.getMembercardRechargeAmount(queryVo);
        Map<Long,Map<String,EmployeePerformanceindicatorResultVo>> membercardRechargeAmountMap = this.handleMembercardRechargeAmountList(membercardRechargeAmountList);

        Map<Long,Map<String,EmployeePerformanceindicatorResultVo>> mergedMap = new HashMap<>();
        this.mergeMap(mergedMap,actualServicechargeMap);
        this.mergeMap(mergedMap,itemProductAmountMap);
        this.mergeMap(mergedMap,membercardFirstMap);
        this.mergeMap(mergedMap,membercardRechargeAmountMap);

        //{"commission_fixed_amount":102,"first_memebercard_commission":0.02,"continue_membercard_commission":0.01}
        String configValue = sysConfigService.selectConfigByKey("commission:membercard:employee");
        JSONObject configValueObj = JSONObject.parseObject(configValue);
        BigDecimal commissionFixedAmount = configValueObj.getBigDecimal("commission_fixed_amount");
        BigDecimal firstMemebercardCommission = configValueObj.getBigDecimal("first_memebercard_commission");
        BigDecimal continueMembercardCommission = configValueObj.getBigDecimal("continue_membercard_commission");

        List<EmployeePerformanceStatisticsResultVo> result = this.convertMap(mergedMap,employeeIdNameMap);
        for(EmployeePerformanceStatisticsResultVo vo:result){
            Map<String,EmployeePerformanceindicatorResultVo> map = vo.getPerformanceindicatorResultVoMap();
            BigDecimal totalPerformanceAmount = new BigDecimal(0);

            for(Map.Entry<String,EmployeePerformanceindicatorResultVo> entry :map.entrySet()){
                EmployeePerformanceindicatorResultVo secondVo = entry.getValue();
                //计算：总金额=项目金额+产品金额
                BigDecimal totalAmount = new BigDecimal(0);
                if(secondVo.getItemAmount() != null){
                    totalAmount = totalAmount.add(secondVo.getItemAmount());
                }
                if(secondVo.getProductAmount() != null){
                    totalAmount = totalAmount.add(secondVo.getProductAmount());
                }
                secondVo.setTotalAmount(totalAmount);
                //计算：会员卡提成=办卡提成比例*首次充值金额+续卡提成比例*续卡金额
                BigDecimal cardCommissionAmount = commissionFixedAmount;
                if(secondVo.getMembercardFirstAmount() != null){
                    cardCommissionAmount = cardCommissionAmount.add(firstMemebercardCommission.multiply(secondVo.getMembercardFirstAmount()));
                }
                if(secondVo.getCardRechargeAmount() != null){
                    cardCommissionAmount = cardCommissionAmount.add(continueMembercardCommission.multiply(secondVo.getCardRechargeAmount()));
                }
                secondVo.setCardCommissionAmount(cardCommissionAmount);
                //计算：提成总计=会员办卡提成+项目提成+产品提成
                BigDecimal commissionTotalAmount = cardCommissionAmount;
                if(secondVo.getItemCommissionAmount() != null){
                    commissionTotalAmount = commissionTotalAmount.add(secondVo.getItemCommissionAmount());
                }
                if(secondVo.getProductCommissionAmount() != null){
                    commissionTotalAmount = commissionTotalAmount.add(secondVo.getProductCommissionAmount());
                }
                secondVo.setCommissionTotalAmount(commissionTotalAmount);
                totalPerformanceAmount = totalPerformanceAmount.add(commissionTotalAmount);
            };
            //员工绩效合计（提成合计）
            vo.setTotalPerformanceAmount(totalPerformanceAmount);
        }
        return result;
    }

    /**
     * 项目维度统计
     * @param dateQueryVo
     * @return 项目维度统计指标汇总合计
     */
    @Override
    public List<ItemsDimensionalStatisticsResultVo> getItemsDimensionalStatisticsList(DateQueryVo dateQueryVo) {
        List<Map<String,Object>> queryResultMapList = operatemanageMapper.selectItemsDimensionalStatisticsList(dateQueryVo);
        List<ItemsDimensionalStatisticsResultVo> result = new LinkedList<>();
        if(CollectionUtils.isEmpty(queryResultMapList)){
            return result;
        }
        //项目指标
        for(Map<String,Object> itemsPerformance:queryResultMapList){
//            Date month = (Date) itemsPerformance.get("createTime");
            String itemName = (String) itemsPerformance.get("itemName");
            BigDecimal sales = (BigDecimal) itemsPerformance.get("itemTotalAmount");
            ItemsDimensionalStatisticsResultVo resultVo = new ItemsDimensionalStatisticsResultVo();
//            resultVo.setMonth(month);
            resultVo.setItemName(itemName);
            resultVo.setSales(sales);
            result.add(resultVo);
        }
        return result;
    }

    /**
     * 产品维度统计
     * @param dateQueryVo
     * @return 项目维度统计指标汇总合计
     */
    @Override
    public List<ProductsDimensionalStatisticsResultVo> getProductsDimensionalStatisticsList(DateQueryVo dateQueryVo) {
        List<Map<String,Object>> queryResultMapList = operatemanageMapper.selectProductsDimensionalStatisticsList(dateQueryVo);
        List<ProductsDimensionalStatisticsResultVo> result = new LinkedList<>();
        if(CollectionUtils.isEmpty(queryResultMapList)){
            return result;
        }
        //项目指标
        for(Map<String,Object> itemsPerformance:queryResultMapList){
//            Date month = (Date) itemsPerformance.get("createTime");
            String productName = (String) itemsPerformance.get("productName");
            BigDecimal sales = (BigDecimal) itemsPerformance.get("productTotalAmount");
            ProductsDimensionalStatisticsResultVo resultVo = new ProductsDimensionalStatisticsResultVo();
//            resultVo.setMonth(month);
            resultVo.setProductName(productName);
            resultVo.setSales(sales);
            result.add(resultVo);
        }
        return result;
    }

    /**
     * 将map转换为实体List
     * @param mergedMap
     * @param employeeIdNameMap
     * @return
     */
    private List<EmployeePerformanceStatisticsResultVo> convertMap(Map<Long, Map<String, EmployeePerformanceindicatorResultVo>> mergedMap,
                                                                   Map<Long, String> employeeIdNameMap) {
        List<EmployeePerformanceStatisticsResultVo> resultVoList = new LinkedList<>();
        if(mergedMap.isEmpty()||employeeIdNameMap.isEmpty()){
            return resultVoList;
        }
        for(Map.Entry<Long, Map<String, EmployeePerformanceindicatorResultVo>> map:mergedMap.entrySet()){
            Long key = map.getKey();
            Map<String,EmployeePerformanceindicatorResultVo> value = map.getValue();
            EmployeePerformanceStatisticsResultVo vo = new EmployeePerformanceStatisticsResultVo();
            vo.setEmployeeId(key);
            vo.setEmployeeName(employeeIdNameMap.get(key));
            vo.setPerformanceindicatorResultVoMap(value);
            resultVoList.add(vo);
        }
        return resultVoList;
    }

    /**
     * 将第二个参数map合并到第一个里面去
     * @param mergedMap 合并后的map
     * @param toMergeMap 被合并的map
     */
    private void mergeMap(Map<Long, Map<String, EmployeePerformanceindicatorResultVo>> mergedMap,
                          Map<Long, Map<String, EmployeePerformanceindicatorResultVo>> toMergeMap) {
        if(toMergeMap.isEmpty()){
            return;
        }
        for(Map.Entry<Long, Map<String, EmployeePerformanceindicatorResultVo>> map:toMergeMap.entrySet()){
            Long employeeId = map.getKey();
            Map<String, EmployeePerformanceindicatorResultVo> secondMap = map.getValue();

            Map<String, EmployeePerformanceindicatorResultVo> finalMap = mergedMap.get(employeeId);
            if(finalMap == null){
                mergedMap.put(employeeId,secondMap);
            }else{
                Set<Map.Entry<String, EmployeePerformanceindicatorResultVo>> set = secondMap.entrySet();
                Iterator<Map.Entry<String, EmployeePerformanceindicatorResultVo>> iterator1 = set.iterator();
                while (iterator1.hasNext()) {
                    Map.Entry<String, EmployeePerformanceindicatorResultVo> map1 = iterator1.next();
                    String date = map1.getKey();
                    EmployeePerformanceindicatorResultVo vo = map1.getValue();

                    EmployeePerformanceindicatorResultVo existVo = finalMap.get(date);
                    if(existVo == null){
                        finalMap.put(date,vo);
                    }else{
                        BeanUtils.copyPropertiesIgnoreNull(vo,existVo);
                        finalMap.put(date,existVo);
                    }
                }
//                mergedMap.put(employeeId,finalMap);
            }
        }
        return;
    }

    /**
     *
     * @param membercardRechargeAmountList  <日期,<会员卡id,充值金额>>
     * @return <员工id,<日期，会员卡续卡充值金额>>
     */
    private Map<Long, Map<String, EmployeePerformanceindicatorResultVo>> handleMembercardRechargeAmountList(Map<String, Map<Long, BigDecimal>> membercardRechargeAmountList) {
        Map<Long, Map<String, EmployeePerformanceindicatorResultVo>> result = new HashMap<>();
        if(membercardRechargeAmountList.isEmpty()){
            return result;
        }
        for(Map.Entry<String, Map<Long, BigDecimal>> map:membercardRechargeAmountList.entrySet()){
            String date = map.getKey();
            Map<Long, BigDecimal> membercardRecargetMap = map.getValue();
            Set<Map.Entry<Long, BigDecimal>> set = membercardRecargetMap.entrySet();
            Iterator<Map.Entry<Long, BigDecimal>> iterator1 = set.iterator();
            while (iterator1.hasNext()){
                Map.Entry<Long, BigDecimal> map1 = iterator1.next();
                Long id = map1.getKey();
                BigDecimal rechargeAmount = map1.getValue();
                BMemberCard memberCard = memberCardService.selectBMemberCardById(id);
                Long recommendEmployeeId = memberCard.getRecommendEmployeeId();
                Map<String, EmployeePerformanceindicatorResultVo> resultVoMap = result.get(recommendEmployeeId);
                if(resultVoMap == null){
                    resultVoMap = new HashMap<>();
                    EmployeePerformanceindicatorResultVo resultVo = new EmployeePerformanceindicatorResultVo();
                    resultVo.setCardRechargeAmount(rechargeAmount);
                    resultVoMap.put(date,resultVo);
                    result.put(recommendEmployeeId,resultVoMap);
                }else{
                    EmployeePerformanceindicatorResultVo resultVo = resultVoMap.get(date);
                    if(resultVo == null){
                        resultVo = new EmployeePerformanceindicatorResultVo();
                        resultVo.setCardRechargeAmount(rechargeAmount);
                        resultVoMap.put(date,resultVo);
                    }else{
                        BigDecimal finalRechargeAmount = resultVo.getCardRechargeAmount().add(rechargeAmount);
                        resultVo.setCardRechargeAmount(finalRechargeAmount);
                        resultVoMap.put(date,resultVo);
                    }
                    result.put(recommendEmployeeId,resultVoMap);
                }
            }
        }
        return result;
    }

    /**
     * 将List转为Map<员工id,<日期，首次充值金额>>
     * @param membercardFirstList
     * @return
     */
    private Map<Long, Map<String, EmployeePerformanceindicatorResultVo>> handleMembercardFirstList(List<Map<String, Object>> membercardFirstList) {
        Map<Long, Map<String, EmployeePerformanceindicatorResultVo>> result = new HashMap<>();
        if(CollectionUtils.isEmpty(membercardFirstList)){
            return result;
        }
        for(Map<String,Object> memberCardFirstMap : membercardFirstList){
            Long recommendEmployeeId = Long.valueOf(String.valueOf(memberCardFirstMap.get("recommendEmployeeId")));
            //带时分秒
            java.sql.Date createTimeDateTime = (java.sql.Date) memberCardFirstMap.get("createTime");
            //不带时分秒
            String createTimeDate = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,createTimeDateTime);

            BigDecimal initAmount = (BigDecimal) memberCardFirstMap.get("initAmount");

            Map<String, EmployeePerformanceindicatorResultVo> membercardFirstMap = result.get(recommendEmployeeId);
            if(membercardFirstMap == null){
                membercardFirstMap = new HashMap<>();
                EmployeePerformanceindicatorResultVo resultVo = new EmployeePerformanceindicatorResultVo();
                resultVo.setMembercardFirstAmount(initAmount);
                membercardFirstMap.put(createTimeDate,resultVo);
                result.put(recommendEmployeeId,membercardFirstMap);
            }else{
                EmployeePerformanceindicatorResultVo membercardFirstMapExist = membercardFirstMap.get(createTimeDate);
                if(membercardFirstMapExist == null){
                    membercardFirstMapExist = new EmployeePerformanceindicatorResultVo();
                    membercardFirstMapExist.setMembercardFirstAmount(initAmount);
                }else{
                    //员工一天内推荐多个会员开卡
                    BigDecimal afterInitAmount = membercardFirstMapExist.getMembercardFirstAmount().add(initAmount);
                    membercardFirstMapExist.setMembercardFirstAmount(afterInitAmount);
                }
                membercardFirstMap.put(createTimeDate,membercardFirstMapExist);
            }
        }
        return result;
    }

    /**
     *
     * @param employeeList
     * @return 返回员工id，员工姓名映射
     */
    private Map<Long, String> convertEmployeeList(List<BEmployee> employeeList) {
        Map<Long,String> idNameMap = new HashMap<>();
        if(CollectionUtils.isEmpty(employeeList)){
            return idNameMap;
        }
        for(BEmployee employee:employeeList){
            idNameMap.put(employee.getId(),employee.getEmployeeName());
        }
        return idNameMap;
    }

    /**
     * 将List转为Map<员工id,<日期，项目金额|产品金额>>
     * @param itemProductAmountList
     * @return <员工id,<日期，项目金额|产品金额|项目提成|产品提成>>
     */
    private Map<Long, Map<String, EmployeePerformanceindicatorResultVo>> handleItemProductAmountList(List<Map<String, Object>> itemProductAmountList) {
        Map<Long, Map<String, EmployeePerformanceindicatorResultVo>> result = new HashMap<>();
        if(CollectionUtils.isEmpty(itemProductAmountList)){
            return result;
        }
        //获取所有项目
        BServiceItems itemsQueryCondition = new BServiceItems();
        List<BServiceItems> serviceItemsList = itemsService.selectBServiceItemsList(itemsQueryCondition);
        Map<Long,BServiceItems> serviceItemsMap = serviceItemsList.stream().collect(Collectors.toMap(BServiceItems::getId,bServiceItems -> bServiceItems));
        //获取所有产品
        BServiceProducts productsQueryCondition = new BServiceProducts();
        List<BServiceProducts> serviceProductsList = productsService.selectBServiceProductsList(productsQueryCondition);
        Map<Long,BServiceProducts> serviceProductsMap = serviceProductsList.stream().collect(Collectors.toMap(BServiceProducts::getId,bServiceProducts -> bServiceProducts));

        for(Map<String,Object> map:itemProductAmountList){
            Long employeeId = Long.valueOf(String.valueOf(map.get("employeeId")));
            //带时分秒
            java.time.LocalDateTime createTimeDateTime = (java.time.LocalDateTime) map.get("createTime");
            Date createTimeDateDate = Date.from((createTimeDateTime.atZone(ZoneId.systemDefault())).toInstant());
            //不带时分秒
            String createTimeDate = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,createTimeDateDate);

            String detailType = (String) map.get("detailType");
            BigDecimal itemTotalAmount = (BigDecimal) map.get("itemTotalAmount");
            BigDecimal productsTotalAmount = (BigDecimal) map.get("productsTotalAmount");

            Long pkId = (Long) map.get("detailPkId");
            BigDecimal itemTotalNum = (BigDecimal) map.get("itemTotalNum");
            BigDecimal productsTotalNum = (BigDecimal) map.get("productsTotalNum");
            //计算：项目提成=各项目固定金额提成*项目数量+各项目固定比例提成*项目金额
            BigDecimal itemCommissionAmount = new BigDecimal(0);
            //计算：产品提成=各产品固定金额提成*产品数量+各产品固定比例提成*产品金额
            BigDecimal productCommissionAmount = new BigDecimal(0);
            if("0".equals(detailType)){
                BServiceItems serviceItems = serviceItemsMap.get(pkId);
                BigDecimal commissionFixedAmount = serviceItems.getCommissionFixedAmount();
                if(commissionFixedAmount == null){
                    commissionFixedAmount = new BigDecimal(0);
                }
                BigDecimal commissionFixedProportion = serviceItems.getCommissionFixedProportion();
                if(commissionFixedProportion == null){
                    commissionFixedProportion = new BigDecimal(0);
                }
                itemCommissionAmount = commissionFixedAmount.multiply(itemTotalNum).add(commissionFixedProportion.multiply(itemTotalAmount));
            }else{
                BServiceProducts serviceProducts = serviceProductsMap.get(pkId);
                BigDecimal commissionFixedAmount = serviceProducts.getCommissionFixedAmount();
                if(commissionFixedAmount == null){
                    commissionFixedAmount = new BigDecimal(0);
                }
                BigDecimal commissionFixedProportion = serviceProducts.getCommissionFixedProportion();
                if(commissionFixedProportion == null){
                    commissionFixedProportion = new BigDecimal(0);
                }
                productCommissionAmount = commissionFixedAmount.multiply(productsTotalNum).add(commissionFixedProportion.multiply(itemTotalAmount));
            }

            Map<String, EmployeePerformanceindicatorResultVo> itemProductAmountMap = result.get(employeeId);
            if(itemProductAmountMap == null){
                itemProductAmountMap = new HashMap<>();
                EmployeePerformanceindicatorResultVo resultVo = new EmployeePerformanceindicatorResultVo();
                if("0".equals(detailType)){
                    resultVo.setItemAmount(itemTotalAmount);
                    resultVo.setItemCommissionAmount(itemCommissionAmount);
                }else{
                    resultVo.setProductAmount(productsTotalAmount);
                    resultVo.setProductCommissionAmount(productCommissionAmount);
                }
                itemProductAmountMap.put(createTimeDate,resultVo);
                result.put(employeeId,itemProductAmountMap);
            }else{
                EmployeePerformanceindicatorResultVo resultVo = itemProductAmountMap.get(createTimeDate);
                if(resultVo == null){
                    resultVo = new EmployeePerformanceindicatorResultVo();
                    if("0".equals(detailType)){
                        resultVo.setItemAmount(itemTotalAmount);
                        resultVo.setItemCommissionAmount(itemCommissionAmount);
                    }else{
                        resultVo.setProductAmount(productsTotalAmount);
                        //项目提成
                        resultVo.setProductCommissionAmount(productCommissionAmount);
                    }
                }else{
                    if("0".equals(detailType)){
                        BigDecimal newItemAmount = resultVo.getItemAmount();
                        resultVo.setItemAmount(newItemAmount.add(itemTotalAmount));
                        resultVo.setItemCommissionAmount(itemCommissionAmount);
                    }else{
                        if(resultVo.getProductAmount() == null){
                            resultVo.setProductAmount(new BigDecimal(0));
                        }
                        if(resultVo.getProductCommissionAmount() == null){
                            resultVo.setProductCommissionAmount(new BigDecimal(0));
                        }
                        BigDecimal newProductsAmount = resultVo.getProductAmount().add(productsTotalAmount);
                        resultVo.setProductAmount(newProductsAmount);
                        //项目提成
                        BigDecimal newProductCommissionAmount = resultVo.getProductCommissionAmount().add(productCommissionAmount);
                        resultVo.setProductCommissionAmount(newProductCommissionAmount);
                    }
                }
                itemProductAmountMap.put(createTimeDate,resultVo);
                result.put(employeeId,itemProductAmountMap);
            }
        }
        return result;
    }

    /**
     * 将list转为Map<员工id，<日期，实际消费金额>>
     * @param actualServicechargeList
     * @return
     */
    private Map<Long, Map<String, EmployeePerformanceindicatorResultVo>> handleActualServicechargeList(List<EmployeePerformanceindicatorResultVo> actualServicechargeList) {
        Map<Long, Map<String, EmployeePerformanceindicatorResultVo>> result = new HashMap<>();
        if(CollectionUtils.isEmpty(actualServicechargeList)){
            return result;
        }
        for(EmployeePerformanceindicatorResultVo resultVo:actualServicechargeList){
            Long employeeId = resultVo.getEmployeeId();
            //带时分秒
            Date createTimeDateTime = resultVo.getCreateTime();
            //不带时分秒
            String createTimeDate = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,createTimeDateTime);
            BigDecimal totalServiceCharge = resultVo.getActualServiceCharge();

            Map<String, EmployeePerformanceindicatorResultVo> actualServicechargeMap = result.get(employeeId);
            if(actualServicechargeMap == null){
                actualServicechargeMap = new HashMap<>();
                actualServicechargeMap.put(createTimeDate,resultVo);
                result.put(employeeId,actualServicechargeMap);
            }else{
                EmployeePerformanceindicatorResultVo totalServiceChargeExist = actualServicechargeMap.get(createTimeDate);
                if(totalServiceChargeExist == null){
                    totalServiceChargeExist = new EmployeePerformanceindicatorResultVo();
                    totalServiceChargeExist.setActualServiceCharge(totalServiceCharge);
                }else{
                    BigDecimal afterServiceCharge = totalServiceChargeExist.getActualServiceCharge().add(totalServiceCharge);
                    totalServiceChargeExist.setActualServiceCharge(afterServiceCharge);
                }
                actualServicechargeMap.put(createTimeDate,totalServiceChargeExist);
            }
        }
        return result;
    }

    /**
     * 获取会员充值金额
     * @param queryVo
     * @return  List Map：<会员卡id：充值金额></会员卡id：充值金额>
     */
    private Map<String,Map<Long, BigDecimal>> getMembercardRechargeAmount(EmployeePerformanceStatisticsQueryVo queryVo) {
        Map<String,Map<Long, BigDecimal>> result = new HashMap<>();
        //获取会员卡充值日志
        List<Map<String,Object>> rechargeLogList = operatemanageMapper.selectRechargeLog(queryVo);
        if(CollectionUtils.isEmpty(rechargeLogList)) {
            return result;
        }
        //充值过的会员卡信息
        Map<String,Map<Long, BigDecimal>> rechargeResult = new HashMap<>();
        List<Long> allRechargeMembercardId = new LinkedList<>();
        List<Long> assignEmployeeMembercardId = new LinkedList<>();
        for(Map<String,Object> rechargeLog :rechargeLogList){
            String operParam = String.valueOf(rechargeLog.get("operParam"));
            String jsonResult = String.valueOf(rechargeLog.get("jsonResult"));
            if(StringUtils.isEmpty(operParam)||StringUtils.isEmpty(jsonResult)){
                continue;
            }
            //带时分秒
            java.time.LocalDateTime operTimeDateTime = (java.time.LocalDateTime) rechargeLog.get("operTime");
            Date operTimeDateDate = Date.from((operTimeDateTime.atZone(ZoneId.systemDefault())).toInstant());
            //不带时分秒
            String operTimeDate = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,operTimeDateDate);

            JSONObject jsonObjectResult = JSONObject.parseObject(jsonResult);
            Integer resultCode = jsonObjectResult.getInteger("code");
            if(resultCode != null && resultCode.equals(HttpStatus.SUCCESS)){
                JSONObject jsonObjectOperParam = JSONObject.parseObject(operParam);
                //会员卡id,注：一个会员卡有可能有多条充值记录,进行合并
                Long id = jsonObjectOperParam.getLong("id");
                String rechargeAmount = jsonObjectOperParam.getString("rechargeAmount");
                if(StringUtils.isNotBlank(rechargeAmount)){
                    Map<Long, BigDecimal> resultMap = rechargeResult.get(operTimeDate);
                    if(resultMap == null){
                        resultMap = new HashMap<>();
                        resultMap.put(id,new BigDecimal(rechargeAmount));
                    }else{
                       BigDecimal rechargeAmountExist =  resultMap.get(id);
                       rechargeAmountExist = rechargeAmountExist.add(new BigDecimal(rechargeAmount));
                       resultMap.put(id,rechargeAmountExist);
                    }
                    allRechargeMembercardId.add(id);
                }
            }
        }
        //如果指定员工，过滤指定员工推荐会员卡数据
        Long employeeId = queryVo.getEmployeeId();
        if(employeeId != null){
            BMemberCard memberCardQueryCondition = new BMemberCard();
            memberCardQueryCondition.setRecommendEmployeeId(employeeId);
            List<BMemberCard> recommendEmployeeMembercardList = memberCardService.selectBMemberCardList(memberCardQueryCondition);
            if(CollectionUtils.isNotEmpty(recommendEmployeeMembercardList)){
                for(BMemberCard memberCard:recommendEmployeeMembercardList){
                    Long membercardId = memberCard.getId();
                    assignEmployeeMembercardId.add(membercardId);
                }
            }
            //取交集
            assignEmployeeMembercardId.retainAll(allRechargeMembercardId);
        }
        Set<Map.Entry<String, Map<Long, BigDecimal>>> entrySet = rechargeResult.entrySet();
        Iterator<Map.Entry<String, Map<Long, BigDecimal>>> iterator = entrySet.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Map<Long, BigDecimal>> entry = iterator.next();
            String date = entry.getKey();
            Map<Long,BigDecimal> rechargeMap = entry.getValue();
            for(Long id :assignEmployeeMembercardId){
                if(rechargeMap.containsKey(id)){
                    //日期和会员卡已在上一步去重合并，无需再次合并
                    result.put(date,rechargeMap);
                }
            }
        }
        return result;
    }
}
