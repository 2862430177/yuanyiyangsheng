import request from '@/utils/request'

// 查询短信消息批量发送定时执行状态列表
export function listBatch(query) {
  return request({
    url: '/marketingmanage/batch/list',
    method: 'get',
    params: query
  })
}

// 查询短信消息批量发送定时执行状态详细
export function getBatch(id) {
  return request({
    url: '/marketingmanage/batch/' + id,
    method: 'get'
  })
}

// 新增短信消息批量发送定时执行状态
export function addBatch(data) {
  return request({
    url: '/marketingmanage/batch',
    method: 'post',
    data: data
  })
}

// 修改短信消息批量发送定时执行状态
export function updateBatch(data) {
  return request({
    url: '/marketingmanage/batch',
    method: 'put',
    data: data
  })
}

// 删除短信消息批量发送定时执行状态
export function delBatch(id) {
  return request({
    url: '/marketingmanage/batch/' + id,
    method: 'delete'
  })
}
