import request from '@/utils/request'

// 查询消息记录（短信|微信|邮件）列表
export function listSms(query) {
  return request({
    url: '/marketingmanage/sms/list',
    method: 'get',
    params: query
  })
}

// 查询消息记录（短信|微信|邮件）详细
export function getSms(id) {
  return request({
    url: '/marketingmanage/sms/' + id,
    method: 'get'
  })
}

// 新增消息记录（短信|微信|邮件）
export function addSms(data) {
  return request({
    url: '/marketingmanage/sms',
    method: 'post',
    data: data
  })
}

// 修改消息记录（短信|微信|邮件）
export function updateSms(data) {
  return request({
    url: '/marketingmanage/sms',
    method: 'put',
    data: data
  })
}

// 删除消息记录（短信|微信|邮件）
export function delSms(id) {
  return request({
    url: '/marketingmanage/sms/' + id,
    method: 'delete'
  })
}
