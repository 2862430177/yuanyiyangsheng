import request from '@/utils/request'

// 查询会员卡信息管理列表
export function listMembercard(query) {
  return request({
    url: '/member/membercard/list',
    method: 'get',
    params: query
  })
}

// 根据会员id或手机号获取唯一会员卡
export function getMembercardInfoByMemberinfo(query) {
  return request({
    url: '/member/membercard/getMembercardInfoByMemberinfo',
    method: 'get',
    params: query
  })
}


// 查询会员卡信息管理详细（带手机号等其他信息）
export function getMembercardInfo(id) {
  return request({
    url: '/member/membercard/getMembercardInfo/' + id,
    method: 'get'
  })
}

// 会员卡充值
export function rechargeMembercard(data) {
  return request({
    url: '/member/membercard/recharge',
    method: 'post',
    data: data
  })
}


// 查询会员卡信息管理详细
export function getMembercard(id) {
  return request({
    url: '/member/membercard/' + id,
    method: 'get'
  })
}

// 新增会员卡信息管理
export function addMembercard(data) {
  return request({
    url: '/member/membercard',
    method: 'post',
    data: data
  })
}

// 修改会员卡信息管理
export function updateMembercard(data) {
  return request({
    url: '/member/membercard',
    method: 'put',
    data: data
  })
}

// 删除会员卡信息管理
export function delMembercard(id) {
  return request({
    url: '/member/membercard/' + id,
    method: 'delete'
  })
}
