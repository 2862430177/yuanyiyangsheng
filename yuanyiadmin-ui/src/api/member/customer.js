import request from '@/utils/request'

// 查询会员信息管理列表
export function listCustomer(query) {
  return request({
    url: '/member/customer/list',
    method: 'get',
    params: query
  })
}

// 查询会员信息附带会员卡信息列表
export function getCustomerInfoWithMemberCard(query) {
  return request({
    url: '/member/customer/getCustomerInfoWithMemberCard',
    method: 'get',
    params: query
  })
}

getCustomerInfoWithMemberCard

// 查询会员信息管理详细
export function getCustomer(id) {
  return request({
    url: '/member/customer/' + id,
    method: 'get'
  })
}

// 新增会员信息管理
export function addCustomer(data) {
  return request({
    url: '/member/customer',
    method: 'post',
    data: data
  })
}

// 修改会员信息管理
export function updateCustomer(data) {
  return request({
    url: '/member/customer',
    method: 'put',
    data: data
  })
}

// 删除会员信息管理
export function delCustomer(id) {
  return request({
    url: '/member/customer/' + id,
    method: 'delete'
  })
}
