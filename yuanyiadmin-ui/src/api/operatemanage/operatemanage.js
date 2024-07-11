import request from '@/utils/request'

// 营业额统计列表
export function listTurnoverStatistics(query) {
  return request({
    url: '/operatemanage/turnoverStatistics/list',
    method: 'get',
    params: query
  })
}
//员工业绩统计
export function listEmployeePerformanceStatistics(query) {
  return request({
    url: '/operatemanage/employeePerformanceStatistics/list',
    method: 'get',
    params: query
  })
}

//项目维度统计
export function listItemsDimensionalStatistics(query) {
  return request({
    url: '/operatemanage/itemsDimensionalStatistics/list',
    method: 'get',
    params: query
  })
}

//产品维度统计
export function listProductsDimensionalStatistics(query) {
  return request({
    url: '/operatemanage/productsDimensionalStatistics/list',
    method: 'get',
    params: query
  })
}

// 获取开始日期到结束日期之间所有日期
export function getDatesBetween(startDate, endDate) {
  const dates = [];
  const currentDate = new Date(startDate);
  const endDateObj = new Date(endDate);
  while (currentDate <= endDateObj) {
    let currentDateStr = currentDate.toISOString ().substr(0, 10)
    dates.push(currentDateStr);
    currentDate.setDate(currentDate.getDate() + 1);
  }
  return dates;
}