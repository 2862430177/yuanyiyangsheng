import request from '@/utils/request'

// 查询服务项目管理列表
export function listServiceitems(query) {
  return request({
    url: '/serviceitems/serviceitems/list',
    method: 'get',
    params: query
  })
}

// 查询服务项目管理详细
export function getServiceitems(id) {
  return request({
    url: '/serviceitems/serviceitems/' + id,
    method: 'get'
  })
}

// 新增服务项目管理
export function addServiceitems(data) {
  return request({
    url: '/serviceitems/serviceitems',
    method: 'post',
    data: data
  })
}

// 修改服务项目管理
export function updateServiceitems(data) {
  return request({
    url: '/serviceitems/serviceitems',
    method: 'put',
    data: data
  })
}

// 删除服务项目管理
export function delServiceitems(id) {
  return request({
    url: '/serviceitems/serviceitems/' + id,
    method: 'delete'
  })
}
