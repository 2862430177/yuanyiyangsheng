import request from '@/utils/request'

// 查询服务流水,一次服务一条流水记录列表
export function listFlow(query) {
  return request({
    url: '/dailyservice/flow/list',
    method: 'get',
    params: query
  })
}

// 查询服务流水,一次服务一条流水记录详细
export function getFlow(id) {
  return request({
    url: '/dailyservice/flow/' + id,
    method: 'get'
  })
}

// 新增服务流水,一次服务一条流水记录
export function addFlow(data) {
  return request({
    url: '/dailyservice/flow',
    method: 'post',
    data: data
  })
}

// 修改服务流水,一次服务一条流水记录
export function updateFlow(data) {
  return request({
    url: '/dailyservice/flow',
    method: 'put',
    data: data
  })
}

//确定结算
export function settleFlow(data) {
  return request({
    url: '/dailyservice/flow/confirmSettle',
    method: 'post',
    data: data
  })
}

//确定重新结算
export function reSettleFlow(data) {
  return request({
    url: '/dailyservice/flow/confirmReSettle',
    method: 'post',
    data: data
  })
}

// 删除服务流水,一次服务一条流水记录
export function delFlow(id) {
  return request({
    url: '/dailyservice/flow/' + id,
    method: 'delete'
  })
}
